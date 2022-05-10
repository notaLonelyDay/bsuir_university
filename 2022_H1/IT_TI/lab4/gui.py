from tkinter import *
from tkinter import ttk
from tkinter import messagebox
from tkinter import filedialog
import main


def remove_values_from_list(the_list, val):
    return [value for value in the_list if value != val]


def choosefile():
    # outfile.delete('1.0', END)
    hashf.delete('1.0', END)
    v.delete('1.0', END)
    w.delete('1.0', END)
    u1.delete('1.0', END)
    u2.delete('1.0', END)
    binarystr = ""
    Plaintext.delete('1.0', END)
    filetypes = (('text files', '*.txt'),
                 ('All files', '*.*'))
    f = filedialog.askopenfile(filetypes=filetypes)
    name = f.name
    global fileContent
    global stringcontent
    with open(name, mode='rb') as file:  # b is important -> binary
        fileContent = list(file.read())
    with open(name, mode='r') as file:  # b is important -> binary
        stringcontent = file.read()
    Plaintext.insert(INSERT, stringcontent)
    Cyphertext.delete('1.0', END)
    btn1['state'] = "normal"
    btn3['state'] = "disabled"


def fileforcheck():
    # outfile.delete('1.0', END)
    hashf.delete('1.0', END)
    v.delete('1.0', END)
    w.delete('1.0', END)
    u1.delete('1.0', END)
    u2.delete('1.0', END)
    binarystr = ""
    Plaintext.delete('1.0', END)
    filetypes = (('text files', '*.txt'),
                 ('All files', '*.*'))
    f = filedialog.askopenfile(filetypes=filetypes)
    name = f.name
    global fileContent
    with open(name, mode='r') as file:  # b is important -> binary
        string = file.read()
    i = 0
    if string.rfind("_") == -1:
        messagebox.showerror("", "Wrong signature!")
    else:
        Plaintext.insert(INSERT, string)
        while string[i] != "_":
            binarystr += string[i]
            i += 1
        ascii_values = []
        for character in binarystr:
            ascii_values.append(ord(character))
        fileContent = ascii_values
        i = string.find("_")+1
        values = []
        global rs
        binarystr = ""
        n = len(string)
        while i < n:
            binarystr += string[i]
            if string[i] == " ":
                values.append(int(binarystr.strip()))
                binarystr = ""
            i += 1
        rs = values
        Cyphertext.delete('1.0', END)
        Cyphertext.insert(INSERT, str(values[0]) + " ")
        Cyphertext.insert(INSERT, str(values[1]))
        btn3['state'] = "normal"


def encrypt():
    hashf.delete('1.0', END)
    hashf.delete('1.0', END)
    v.delete('1.0', END)
    w.delete('1.0', END)
    u1.delete('1.0', END)
    u2.delete('1.0', END)
    # outfile.delete('1.0', END)

    Cyphertext.delete('1.0', END)
    binarystr = ""
    p_goodtext = main.is_num(p.get("1.0", END))
    q_goodtext = main.is_num(q.get("1.0", END))
    x_goodtext = main.is_num(x.get("1.0", END))
    k_goodtext = main.is_num(k.get("1.0", END))
    h_goodtext = main.is_num(h.get("1.0", END))
    if (p_goodtext != "") & (q_goodtext != "") & (x_goodtext != "") & (k_goodtext != "") & (h_goodtext != ""):
        input = Plaintext.get("1.0", END)
        if (int(h_goodtext) > 1) & (int(h_goodtext) < int(p_goodtext) - 1):
            if (int(x_goodtext) > 0) & (int(x_goodtext) < int(q_goodtext)):
                if (int(k_goodtext) > 0) & (int(k_goodtext) < int(q_goodtext)):
                    r, s, hf, g, y = main.encrypt(fileContent, int(p_goodtext), int(q_goodtext), int(x_goodtext),
                                                  int(k_goodtext), int(h_goodtext))
                    if r == "":
                        messagebox.showerror("", "r or s == 0 try with another k")
                    elif r == "g<1":
                        messagebox.showerror("", "g must be > 1, try another h")
                    elif r == "notprime":
                        messagebox.showerror("", "p or q is not prime")
                    elif r == "notdel":
                        messagebox.showerror("", "q is not del of p-1")
                    else:
                        Cyphertext.insert(INSERT, str(r) + " " + str(s))
                        hashf.insert(INSERT, str(hf))
                        if len(fileContent) != 0:
                            with filedialog.asksaveasfile(mode='w', defaultextension=".txt") as file:
                                file.write(str(stringcontent) + "_")
                                file.write(str(r) + ' ')
                                file.write(str(s) + " ")
                        else:
                            with filedialog.asksaveasfile(mode='w', defaultextension=".txt") as file:
                                file.write("_" + str(r) + " ")
                                file.write(str(s) + " ")
                        # with filedialog.asksaveasfile(mode='w', defaultextension=".txt") as file:
                        #     cont = file.read()
                        # outfile.insert(INSERT, cont)
                else:
                    messagebox.showerror("", "k must be 0<h<q")
            else:
                messagebox.showerror("", "x must be 0<x<q")
        else:
            messagebox.showerror("", "h must be 1<h<p-1")
    else:
        messagebox.showerror("", "Incorrect input. Check and try again")


def check():
    # outfile.delete('1.0', END)
    hashf.delete('1.0', END)
    v.delete('1.0', END)
    w.delete('1.0', END)
    u1.delete('1.0', END)
    u2.delete('1.0', END)
    binarystr = ""
    p_goodtext = main.is_num(p.get("1.0", END))
    q_goodtext = main.is_num(q.get("1.0", END))
    x_goodtext = main.is_num(x.get("1.0", END))
    k_goodtext = main.is_num(k.get("1.0", END))
    h_goodtext = main.is_num(h.get("1.0", END))
    if (p_goodtext != "") & (q_goodtext != "") & (x_goodtext != "") & (h_goodtext != ""):
        input = Plaintext.get("1.0", END)
        if (int(h_goodtext) > 1) & (int(h_goodtext) < int(p_goodtext) - 1):
            if (int(x_goodtext) > 0) & (int(x_goodtext) < int(q_goodtext)):
                if (int(k_goodtext) > 0) & (int(k_goodtext) < int(q_goodtext)):
                    hf, w_value, u1_value, u2_value, v_value = main.check(fileContent, int(p_goodtext), int(q_goodtext),
                                                                          rs[0],
                                                                          rs[1], int(h_goodtext), int(x_goodtext))
                    if hf == "":
                        messagebox.showerror("", "r or s == 0 try with another k")
                    elif hf == "notprime":
                        messagebox.showerror("", "p or q is not prime")
                    elif hf == "notdel":
                        messagebox.showerror("", "q is not del of p-1")
                    else:
                        # Cyphertext.insert(INSERT, str(r) + " " + str(s))
                        hashf.insert(INSERT, str(hf))
                        v.insert(INSERT, str(v_value))
                        w.insert(INSERT, str(w_value))
                        u1.insert(INSERT, str(u1_value))
                        u2.insert(INSERT, str(u2_value))
                        if rs[0] == v_value:
                            messagebox.showinfo("", "Подпись верна!")
                        else:
                            messagebox.showinfo("", "Подпись неверна!")
                else:
                    messagebox.showerror("", "k must be 0<x<q")
            else:
                messagebox.showerror("", "x must be 0<x<q")
        else:
            messagebox.showerror("", "h must be 1<h<p-1")
    else:
        messagebox.showerror("", "Incorrect input. Check and try again")


window = Tk()
window.title("Шифрование 4")
window.geometry('1280x720')
# window.config(bg="black")
window.configure(bg='#cfd8dc')

# label


lbl1 = Label(window, text="", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=10, y=30)
lbl2 = Label(window, text="r, s", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=5, y=375)
lbl4 = Label(window, text="P", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=1030, y=6)
lbl5 = Label(window, text="Q", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=1030, y=175)
lbl6 = Label(window, text="h", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=1030, y=55)
lbl8 = Label(window, text="x", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=1030, y=105)
lbl9 = Label(window, text="k", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=1150, y=55)
lbl10 = Label(window, text="w", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=150, y=375)
lbl11 = Label(window, text="u1", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=295, y=375)
lbl12 = Label(window, text="u2", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=440, y=375)
lbl13 = Label(window, text="v", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=585, y=375)
lbl14 = Label(window, text="Hash", font=('Times New Roman', 11), fg="black", bg="#cfd8dc").place(x=730, y=375)

# Combobox creation

Plaintext = Text(window, height=13, width=110, bg="white", font=('Times New Roman', 11))
Plaintext.place(x=190, y=80)

Cyphertext = Text(window, height=1, width=12, bg="white", font=('Times New Roman', 11))
Cyphertext.place(x=5, y=400)

hashf = Text(window, height=1, width=12, bg="white", font=('Times New Roman', 11))
hashf.place(x=730, y=400)

# outfile = Text(window, height=10, width=110, bg="white", font=('Times New Roman', 11))
# outfile.place(x=5, y=470)

p = Text(window, height=1, width=10, bg="white", font=('Times New Roman', 11))
p.place(x=1030, y=30)
# p.insert(INSERT, "89884656743115796742429711405763364460177151692783429800884652449310979263752253529349195459823881715145796498046459238345428121561386626945679753956400077352882071663925459750500807018254028771490434021315691357123734637046894876123496168716251735252662742462099334802433058472377674408598573487858308054417")

q = Text(window, height=1, width=10, bg="white", font=('Times New Roman', 11))
q.place(x=1030, y=200)
# q.insert(INSERT, "1193447034984784682329306571139467195163334221569")

k = Text(window, height=1, width=10, bg="white", font=('Times New Roman', 11))
k.place(x=1150, y=80)
# k.insert(INSERT, "6274")

h = Text(window, height=1, width=10, bg="white", font=('Times New Roman', 11))
h.place(x=1030, y=80)
# h.insert(INSERT, "3")

x = Text(window, height=1, width=10, bg="white", font=('Times New Roman', 11))
x.place(x=1030, y=125)
# x.insert(INSERT, "11934")

w = Text(window, height=1, width=12, bg="white", font=('Times New Roman', 11))
w.place(x=150, y=400)

u1 = Text(window, height=1, width=12, bg="white", font=('Times New Roman', 11))
u1.place(x=295, y=400)

u2 = Text(window, height=1, width=12, bg="white", font=('Times New Roman', 11))
u2.place(x=440, y=400)

v = Text(window, height=1, width=12, bg="white", font=('Times New Roman', 11))
v.place(x=585, y=400)

btn = Button(window, text="File to check", command=fileforcheck, padx=5, pady=0,
             font=('Times New Roman', 11))
btn.config(bg="white")
btn.place(x=500, y=20)

btn1 = Button(window, text="Sign", command=encrypt, padx=5, pady=0, font=('Times New Roman', 11),
              state='disabled')
btn1.config(bg="white")
btn1.place(x=400, y=20)

btn2 = Button(window, text="File to sign", command=choosefile, padx=5, pady=0, font=('Times New Roman', 11))
btn2.config(bg="white")
btn2.place(x=250, y=20)

btn3 = Button(window, text="Check sign", command=check, padx=5, pady=0, font=('Times New Roman', 11), state="disabled")
btn3.config(bg="white")
btn3.place(x=650, y=20)

# btn.grid(column=3, row=0)
window.mainloop()
