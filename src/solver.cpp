#include <iostream>
using namespace std;

int a[9][9];
int ok;

int check(int n, int i, int j)
{
    for (int k = 0; k < 9; ++k)
    {
        if (a[i][k] == n || a[k][j] == n)
            return 0;
    }
    int sx = (i / 3) * 3, sy = (j / 3) * 3;
    for (int x = sx; x < sx + 3; ++x)
    {
        for (int y = sy; y < sy + 3; ++y)
        {
            if (a[x][y] == n)
                return 0;
        }
    }
    return 1;
}

void solve(int i, int j)
{
    if (i == 9)
    {
        ok = 1;
        for (int i = 0; i < 9; ++i)
        {
            for (int j = 0; j < 9; ++j)
                cout << a[i][j] << ' ';
            cout << '\n';
        }
        return;
    }
    if (j == 9)
    {
        solve(i + 1, 0);
        return;
    }
    if (a[i][j])
    {
        solve(i, j + 1);
        return;
    }
    for (int n = 1; n <= 9; ++n)
    {
        if (check(n, i, j))
        {
            a[i][j] = n;
            solve(i, j + 1);
        }
    }
    a[i][j] = 0;
}

int main()
{
    for (int i = 0; i < 9; ++i)
    {
        for (int j = 0; j < 9; ++j)
            cin >> a[i][j];
    }
    solve(0, 0);
    if (!ok)
        cout << "Khong giai duoc";
}
