#include <Windows.h>
#include <WindowsX.h>
//#include <String.h>
//#include <string>
#include <debugapi.h>

#pragma comment(lib, "Msimg32.lib")

HDC hdcBack;
HANDLE hbmBack;
RECT clientRect;

BITMAP bitmap;
HANDLE hBmp;

POINT rectCoordinates = { 0 };
POINT mousePosOnRect;
int mousePressed = 0;
int offset = 13;

int rectHeight;
int rectWidth;

enum Modes {
	Auto, Manual
};
enum Modes wMode = Manual;

enum TStatus {
	Timer, KernelTimer
};
enum TStatus curTimer = Timer;
HANDLE kernelTimer;

HBRUSH CurrentBackColor;
HBRUSH PinkBR;
HBRUSH GrayBR;

int durationX = 10;
int durationY = 10;

void autoMode(HWND hWnd, UINT uMsg, UINT_PTR idEvent, DWORD dwTime)
{
	int newXPos = rectCoordinates.x + durationX;
	int newYPos = rectCoordinates.y + durationY;

	if (newXPos < 0 || newXPos + rectWidth > clientRect.right)
		durationX = -durationX;
	else if (newYPos < 0 || newYPos + rectHeight > clientRect.bottom)
		durationY = -durationY;
	
	rectCoordinates.x += durationX;
	rectCoordinates.y += durationY;
	
	InvalidateRect(hWnd, NULL, FALSE);
}

HANDLE threadId;

void ChangeMode(HWND hWnd)
{
	if (wMode == Manual)
	{
		SetTimer(hWnd, 0, 10, autoMode);

		curTimer = Timer;
		wMode = Auto;
	}
	else if (wMode == Auto)
	{
		if (curTimer == Timer)
			KillTimer(hWnd, 0);
		else
		{
			CancelWaitableTimer(kernelTimer);			
			WaitForSingleObject(threadId, INFINITE);
		}

		CurrentBackColor = GrayBR;
		wMode = Manual;
	}
}

VOID CALLBACK TimerAPCProc(LPVOID lpArg, DWORD dwTimerLowValue,	DWORD dwTimerHighValue)
{
	SendMessage(lpArg, WM_USER, 0, 0);
}

DWORD WINAPI TimerThread(LPVOID lpParam)
{
	__int64         qwDueTime;
	LARGE_INTEGER   liDueTime;
	qwDueTime = -1 * 10;

	liDueTime.LowPart = (DWORD)(qwDueTime & 0xFFFFFFFF);
	liDueTime.HighPart = (LONG)(qwDueTime >> 32);

	SetWaitableTimer(kernelTimer, &liDueTime, 50, TimerAPCProc, lpParam, FALSE);

	//String s;
	DWORD waited = WaitForSingleObject(kernelTimer, 100);
	while (waited == WAIT_OBJECT_0)
	{
		SleepEx(INFINITE, TRUE);
		waited = WaitForSingleObject(kernelTimer, 100);
	}
	//MessageBox(NULL, L"", L"", MB_OK);
	OutputDebugString("asd");
	return 0;
}

void setMode(HWND hWnd)
{
	if (wMode != Auto)
		return;

	if (curTimer == Timer)
	{
		KillTimer(hWnd, 0);
		
		threadId = CreateThread(NULL, 0, TimerThread, hWnd, 0, 0);

		CurrentBackColor = PinkBR;
		curTimer = KernelTimer;
	}
	else
	{		
		CancelWaitableTimer(kernelTimer);
		WaitForSingleObject(threadId, INFINITE);

		SetTimer(hWnd, 0, 10, autoMode);

		CurrentBackColor = GrayBR;
		curTimer = Timer;
	}
}

void initBuf(HWND hWnd, int width, int height)
{
	HDC hdcWindow;
	hdcWindow = GetDC(hWnd);
	hdcBack = CreateCompatibleDC(hdcWindow);
	hbmBack = CreateCompatibleBitmap(hdcWindow, width, height);
	SaveDC(hdcBack);
	SelectObject(hdcBack, hbmBack);
	ReleaseDC(hWnd, hdcWindow);	
}

void finBuf()
{
	if (hdcBack)
	{
		RestoreDC(hdcBack, -1);
		DeleteObject(hbmBack);
		DeleteObject(hdcBack);
	}
}

BOOL sizeChanged(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	GetClientRect(hWnd, &clientRect);
	finBuf();
	initBuf(hWnd, clientRect.right, clientRect.bottom);	
	InvalidateRect(hWnd, NULL, FALSE);
}

BOOL onPaint(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	FillRect(hdcBack, &clientRect, CurrentBackColor);

	paintPic(hWnd, hdcBack);

	PAINTSTRUCT ps;
	HDC hdc = BeginPaint(hWnd, &ps);
	BitBlt(hdc, 0, 0, clientRect.right, clientRect.bottom, hdcBack, 0, 0, SRCCOPY);
	EndPaint(hWnd, &ps);
}

BOOL paintPic(HWND hWnd, HDC hdc)
{
	hBmp = LoadImage(NULL, L"C:\\Users\\user\\Desktop\\anime.bmp", IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE);
	GetObject(hBmp, sizeof(bitmap), &bitmap);

	rectHeight = 180;
	rectWidth = 200;

	HDC hMemDC = CreateCompatibleDC(hdc);
	HBITMAP hOldBmp = (HBITMAP)SelectObject(hMemDC, hBmp);
	if (hOldBmp)
	{
		SetMapMode(hMemDC, GetMapMode(hdc));
		TransparentBlt(hdc, rectCoordinates.x, rectCoordinates.y, rectWidth, rectHeight, hMemDC, 0, 0, bitmap.bmWidth, bitmap.bmHeight, RGB(255, 255, 255));
		SelectObject(hMemDC, hOldBmp);
	}
	DeleteDC(hMemDC);
}

BOOL leftClicked(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	int x = LOWORD(lParam);
	int y = HIWORD(lParam);

	
	mousePosOnRect.x = x - rectCoordinates.x;
	mousePosOnRect.y = y - rectCoordinates.y;

	if ((mousePosOnRect.x >= 0) && (mousePosOnRect.x <= rectWidth) && (mousePosOnRect.y >= 0) && (mousePosOnRect.y <= rectHeight))
		mousePressed = 1;
}

BOOL mouseMove(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	if (!mousePressed)
		return;

	int x = LOWORD(lParam);
	int y = HIWORD(lParam);

	if ((x - mousePosOnRect.x >= clientRect.left) && (x - mousePosOnRect.x <= clientRect.right - rectWidth))
		rectCoordinates.x = x - mousePosOnRect.x;
	else
		if (x - mousePosOnRect.x < clientRect.left)
			rectCoordinates.x = 0;
		else
			rectCoordinates.x = clientRect.right - rectWidth;

	if ((y - mousePosOnRect.y >= clientRect.top) && (y - mousePosOnRect.y <= clientRect.bottom - rectHeight))
		rectCoordinates.y = y - mousePosOnRect.y;
	else
		if (y - mousePosOnRect.y < clientRect.top)
			rectCoordinates.y = 0;
		else
			rectCoordinates.y = clientRect.bottom - rectHeight;
	InvalidateRect(hWnd, NULL, FALSE);
}

BOOL leftUp(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	mousePressed = 0;
}

BOOL mouseWheel(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	if (mousePressed)
		return;

	int fwKey = GET_KEYSTATE_WPARAM(wParam);
	int zDelta = GET_WHEEL_DELTA_WPARAM(wParam);

	if (fwKey == MK_SHIFT)
		if ((rectCoordinates.x - (zDelta / 25) >= clientRect.left) && (rectCoordinates.x - (zDelta / 25) <= clientRect.right - rectWidth))
			rectCoordinates.x -= (zDelta / 25);
		else
			rectCoordinates.x += (zDelta / 25);
	else
		if ((rectCoordinates.y + (zDelta / 25) >= clientRect.top) && (rectCoordinates.y + (zDelta / 25) <= clientRect.bottom - rectHeight))
			rectCoordinates.y += (zDelta / 25);
		else
			rectCoordinates.y -= (zDelta / 25);

	InvalidateRect(hWnd, NULL, FALSE);
}

BOOL keyDown(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	if (mousePressed)
		return;

	if (GetKeyState(VK_UP) < 0 && GetKeyState(VK_RIGHT) < 0) {
		if (rectCoordinates.y - offset <= clientRect.top)
			rectCoordinates.y += offset;
		else {
			rectCoordinates.y -= offset;
			rectCoordinates.x += offset;
		}
	}
	else if (GetKeyState(VK_UP) < 0 && GetKeyState(VK_LEFT) < 0) {
		if (rectCoordinates.x - offset <= clientRect.left)
			rectCoordinates.x += offset;
		else {
			rectCoordinates.y -= offset;
			rectCoordinates.x -= offset;
		}
	}
	else if (GetKeyState(VK_DOWN) < 0 && GetKeyState(VK_LEFT) < 0) {
		if (rectCoordinates.y + offset >= clientRect.bottom - rectHeight)
			rectCoordinates.y -= offset;
		else {
			rectCoordinates.x -= offset;
			rectCoordinates.y += offset;
		}
	}
	else if (GetKeyState(VK_DOWN) < 0 && GetKeyState(VK_RIGHT) < 0) {
		if (rectCoordinates.y + offset >= clientRect.bottom - rectHeight)
			rectCoordinates.y -= offset;
		else {
			rectCoordinates.x += offset;
			rectCoordinates.y += offset;
		}
	}

	switch (wParam) {
	case VK_UP:
		if (rectCoordinates.y - offset <= clientRect.top)
			rectCoordinates.y += offset;
		else
			rectCoordinates.y -= offset;
		break;
	case VK_DOWN:
		if (rectCoordinates.y + offset >= clientRect.bottom - rectHeight)
			rectCoordinates.y -= offset;
		else
			rectCoordinates.y += offset;
		break;
	case VK_LEFT:
		if (rectCoordinates.x - offset <= clientRect.left)
			rectCoordinates.x += offset;
		else
			rectCoordinates.x -= offset;
		break;
	case VK_RIGHT:
		if (rectCoordinates.x + offset >= clientRect.right - rectWidth)
			rectCoordinates.x -= offset;
		else
			rectCoordinates.x += offset;
		break;
	case VK_RETURN:
		ChangeMode(hWnd);
		break;
	case VK_CONTROL:
		setMode(hWnd);
		break;
	}

	InvalidateRect(hWnd, NULL, FALSE);
}

BOOL OnWindowCreate(HWND hWnd)
{
	PinkBR = CreateSolidBrush(RGB(200, 100, 100));
	GrayBR = CreateSolidBrush(RGB(255, 255, 255));

	CurrentBackColor = GrayBR;

	kernelTimer = CreateWaitableTimer(NULL, FALSE, NULL);
}

LRESULT CALLBACK MainWindowProc(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
	switch (uMsg)
	{
	case WM_CREATE:
		OnWindowCreate(hWnd);
		return 0;
	case WM_DESTROY:
		finBuf();
		CloseHandle(kernelTimer);
		PostQuitMessage(0);
		return 0;
	case WM_SIZE:
		sizeChanged(hWnd, uMsg, wParam, lParam);
		return 0;
	case WM_PAINT:
		onPaint(hWnd, uMsg, wParam, lParam);
		return 0;
	case WM_LBUTTONDOWN:
		leftClicked(hWnd, uMsg, wParam, lParam);
		return 0;
	case WM_MOUSEMOVE:
		mouseMove(hWnd, uMsg, wParam, lParam);
		return 0;
	case WM_LBUTTONUP:
		leftUp(hWnd, uMsg, wParam, lParam);
		return 0;
	case WM_MOUSEWHEEL:
		mouseWheel(hWnd, uMsg, wParam, lParam);
		return 0;
	case WM_KEYDOWN:
		keyDown(hWnd, uMsg, wParam, lParam);
		return 0;
	case WM_USER:
		autoMode(hWnd, 0, 0, 0);
		return 0;
	}
	return DefWindowProc(hWnd, uMsg, wParam, lParam);
}

ATOM RegisterMyClass(HINSTANCE hInstance)
{
	WNDCLASSEX wcex;

	wcex.cbSize = sizeof(WNDCLASSEX);
	wcex.style = 0;
	wcex.lpfnWndProc = MainWindowProc;
	wcex.cbClsExtra = 0;
	wcex.cbWndExtra = 0;
	wcex.hInstance = hInstance;
	wcex.hIcon = LoadIcon(NULL, IDI_APPLICATION);
	wcex.hCursor = LoadCursor(NULL, IDC_ARROW);
	wcex.hbrBackground = (HBRUSH)(COLOR_WINDOW);
	wcex.lpszMenuName = NULL;
	wcex.lpszClassName = L"MainWindowClass";
	wcex.hIconSm = wcex.hIcon;

	return RegisterClassEx(&wcex);
}

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, INT nCmdShow)
{
	HWND hMainWindow;
	MSG msg;

	if (!RegisterMyClass(hInstance))
	{
		return 0;
	}

	hMainWindow = CreateWindowEx(NULL, L"MainWindowClass", L"Lab2", WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, 
		CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, NULL, NULL, hInstance, NULL);

	ShowWindow(hMainWindow, nCmdShow);

	while (GetMessage(&msg, 0, 0, 0))
	{
		//if (!IsDialogMessage(hTab, &msg))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}

	return 0;
}