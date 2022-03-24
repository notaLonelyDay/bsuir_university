"""
RFC868 Time:

    Desc:   
        Query a time server using RFC 868 standard. 

        Multi-threading capable and thread safe. 
        Each GUI instance communicates with an 
        independent time-keeping thread.

    Depends:    
        Python3.4, pytz

    Author:     
        Mike Gonzalez (mgonz50@rutgers.edu)

Usage:
    ./python time.py

"""

import threading, socket, math, datetime, time  # standard modules
from tkinter import *
import pytz  # python timezone module, non-standard

tz_main = 'GMT'  # main timezone
tz_alt = 'US/Eastern'  # alternate timezone
prog_title = 'RFC 868 Time'  # program title
time_serv = '132.163.96.5'  # resolves to 129.6.15.28
port = 37
time_serv = '127.0.0.1'  # resolves to 129.6.15.28
port = 4444


def query_Time():  # query time server, returns timekeeper object
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # query server
    s.connect((time_serv, port))  # always port 37
    data = s.recv(4)  # receive 32 bits, 4 bytes
    raw_time = int.from_bytes(data, byteorder='big')  # decode data
    return time_Keeper(raw_time, tz_main, tz_alt)  # calculate date and time


def sync_Time(gui):  # sync button handler, gui/timekeeper communication
    gui.toggle_buttons()  # toggle buttons on/off
    tk = query_Time()  # time_Keeper object
    tk.bind_gui(gui)  # bind gui to timekeeping thread
    if (gui.get_opt_keeptime()):
        tk.start_tk_thread()  # start timekeeper thread
    else:
        tk.update_gui()  # communicate with gui just once


class time_Keeper:  # threaded timekeeping class
    epoch = 2208988800  # convert RFC868 to POSIX time
    dt_format = '(%Z) %I:%M:%S %p'  # datetime format

    def __init__(self, time_rfc868, tzm, tza):  # rfc868 time, timezone main, tz alt
        self.tzm = tzm
        self.tza = tza
        self.dt = datetime.datetime.fromtimestamp(
            time_rfc868 - self.epoch,  # convert to epoch time
            pytz.timezone(tzm))
        self.dta = self.dt.astimezone(pytz.timezone(tza))  # alt date time
        self.gui = None
        self.renew_tk_thread()  # set thread_run and initialize tk_thread

    def start_tk_thread(self):  # timekeeping thread
        self.tk_thread.start()

    def notify_gui(self):  # bind timekeeper to interface
        self.gui.set_timekeeper(self)

    def bind_gui(self, gui):  # bind an interface to this timekeeper
        self.gui = gui
        self.notify_gui()

    def update_gui(self):  # update GUI time
        if (self.gui): self.gui.update_timestr()  # set timestr just once

    def time_Worker(self):  # thread worker
        self.update_gui()
        time.sleep(1.0)  # sleep for a second before starting
        while self.thread_run:
            self.tick()
            self.update_gui()
            time.sleep(1.0)

    def renew_tk_thread(self):  # allow for thread to be restarted
        self.tk_thread = threading.Thread(target=self.time_Worker)  # renew thread
        self.thread_run = True

    def stop_join_tk_thread(self):
        self.thread_run = False  # give thread stop message
        if (self.tk_thread.is_alive()): self.tk_thread.join()  # wait for thread to close
        self.renew_tk_thread()  # allow for thread to be restarted

    def tick(self):  # tick timekeeper 1 second
        delta = datetime.timedelta(seconds=1)  # static value
        self.dt = (self.dt + delta).replace(tzinfo=pytz.timezone(self.tzm))
        self.dta = self.dt.astimezone(pytz.timezone(self.tza))

    def to_str(self):  # main time, alternate time to string
        return (self.dt.strftime(self.dt_format) + '\n\n' + self.dta.strftime(self.dt_format))


class time_GUI:  # GUI class bound to timekeeping thread
    def __init__(self):
        # GUI vars
        self.root = Tk()
        self.kt_check = IntVar()  # keep time checkbox
        self.timestr = StringVar()  # timekeeping string
        self.tk = None  # timekeeper bound to this GUI
        # setup root widget
        self.root.wm_title(prog_title)
        self.root.geometry('325x300')
        self.root.resizable(width=FALSE, height=FALSE)
        # create frame for buttons and populate it
        self.button_frame = Frame(self.root)
        self.sync_button = Button(self.button_frame, text='Sync')
        self.kt_button = Checkbutton(self.button_frame, text=' Keep Time')
        # create frame for time and insert label
        self.time_frame = Frame(self.root)
        self.time_label = Label(self.time_frame, textvariable=self.timestr, font=('', 20))  # time label
        # place reset button at the bottom
        self.rst_button = Button(self.root, text='Reset')
        self.rst_button.config(state=DISABLED)  # disable at first
        # list of buttons
        self.buttons = [self.sync_button, self.kt_button, self.rst_button]
        # Pack everything
        self.button_frame.pack(fill=X, padx=10, pady=10)
        self.sync_button.pack(fill=X)
        self.kt_button.pack(side=LEFT, pady=10)
        self.time_frame.pack(fill=BOTH, pady=30)
        self.time_label.pack()
        self.rst_button.pack(side=BOTTOM, anchor=E, padx=10, pady=10)
        # Event handlers
        self.root.protocol('WM_DELETE_WINDOW', self.handle_Close)  # thread safe close handler
        self.sync_button.config(command=lambda: sync_Time(self))
        self.kt_button.config(variable=self.kt_check)
        self.rst_button.config(command=self.handle_Reset)

    def mainloop(self):
        self.root.mainloop()

    def toggle_buttons(self):  # activate disabled buttons and vice-versa
        for button in self.buttons:
            if button.cget('state') == 'disabled':  # enable the disabled
                button.config(state=NORMAL)
            else:  # disable everything else
                button.config(state=DISABLED)

    def deselect_keeptime(self):  # deselect checkbox
        self.kt_button.deselect()

    def set_timekeeper(self, tk):
        self.tk = tk

    def get_timekeeper(self):
        return self.tk

    def handle_Close(self):  # close gui+timekeeper pair
        if (self.tk): self.tk.stop_join_tk_thread()  # stop then join timekeeping thread
        self.root.destroy()

    def handle_Reset(self):  # reset application
        if (self.tk): self.tk.stop_join_tk_thread()
        self.clear_timestr()
        self.toggle_buttons()
        self.deselect_keeptime()

    def update_timestr(self):  # update time string
        if (self.tk): self.timestr.set(self.tk.to_str())

    def clear_timestr(self):
        self.timestr.set('')

    def get_opt_keeptime(self):
        return (self.kt_check.get() == 1)


# Main
time_GUI().mainloop()
