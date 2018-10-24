The exponential el-gamal program is self contained. There are 3 files:
1) Main.java
2) BSGS.java
3) README.txt (this file)

You just need to compile the Main.java and BSGS.java and then execute Main.java.

The code generates all el-gamal parameters with a 29-bit number. As discussed in class this mostly works, but I have some issues. We weren't able to debug in time for the deadline so here it is. I use the myMod function which was Kiran's version of square and multiply. My version was a little bit different (he uses strings, I used built-in functions like bitLength, testBit, etc.) but they both worked so we used his code.

I also use BSGS for the factorization at the end. Again, some parameters may be out of range which explains why it sometimes doesn't work but I didn't have time to fully debug.

The output should look like this:

Bob
p: 517346539
a: 275982843
d: 228853931
B: 192549495
Original message: 501069767
Encrypted message using exponential el-gamal: 119336256

Alice
i: 74088570
ke: 102826317
km: 9591122
y: 419673334
Alg. 1: Exponent found with xb = 20879 : 
119336256 = 275982843^501069767 mod517346539
Decrypted message: 501069767
