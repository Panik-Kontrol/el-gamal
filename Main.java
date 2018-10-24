import java.math.BigInteger;
import java.util.Random;

public class Main {
	private static final int BITLENGTH = 29;
	
	public static void main(String[] args) {
		Random r = new Random();
		/*BigInteger p = BigInteger.probablePrime(BITLENGTH,r);
        BigInteger a = BigInteger.valueOf(0);

        //System.out.println("p_temp: " + p_temp);
        for(BigInteger i = BigInteger.valueOf(2); !i.mod(p.subtract(BigInteger.valueOf(2))).equals(BigInteger.ZERO); i = i.nextProbablePrime()) {
        	//System.out.println("i: " + i);
        	BigInteger temp = p.gcd(i);
        	if(temp == BigInteger.valueOf(1)) {
        		a = i;
        		break;
        	}
        }
        if(a == BigInteger.valueOf(0)) {
        	System.out.print("ERROR: Couldn't find a generator!");
        }
        else {
        	System.out.print("p: " + p);
        	System.out.print("a: " + a);
        }*/
		
        BigInteger p = BigInteger.valueOf(29);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger d = getRandomNum(p, r);
        BigInteger B = myPow(a,d,p);
        System.out.println("Bob");
        System.out.println("p: " + p);
        System.out.println("a: " + a);
        System.out.println("d: " + d);
        System.out.println("B: " + B);
        
        BigInteger i = getRandomNum(p, r);
        BigInteger ke = myPow(a,i,p);
        BigInteger km = myPow(B,i,p);
        BigInteger x = new BigInteger("Andrew".getBytes());
        BigInteger y = x.multiply(km).mod(p);
        System.out.println("\nAlice");
        System.out.println("i: " + i);
        System.out.println("ke: " + ke);
        System.out.println("km: " + km);
        System.out.println("y: " + y);       
        
        
        BigInteger km2 = myPow(ke,d,p);
        BigInteger x2 = y.multiply(km2.modInverse(p));
        System.out.println(x2.toByteArray());
	}
	
	public static BigInteger crt(BigInteger dec, BigInteger d, BigInteger p, BigInteger q, BigInteger n) {
	
		
		BigInteger yp = dec.mod(p);
		BigInteger yq = dec.mod(q);
		
		BigInteger dp = d.mod(p.subtract(BigInteger.ONE));
		BigInteger dq = d.mod(q.subtract(BigInteger.ONE));
		
		BigInteger xp = myPow(yp,dp,p);
		BigInteger xq = myPow(yq,dq,q);
		
		BigInteger cp = q.modInverse(p);
		BigInteger cq = p.modInverse(q);
		
		BigInteger res1 = q.multiply(cp).multiply(xp);
		BigInteger res2 = p.multiply(cq).multiply(xq);
		
		return new BigInteger(res1.add(res2).mod(n).toString());
	}

	private static BigInteger myPow(BigInteger base, BigInteger exponent, BigInteger mod) {
		String exponentBits = exponent.toString(2);
		BigInteger res = new BigInteger(base.toString());

		for (int i = 1; i < exponentBits.length(); i++) {
			res = res.multiply(res).mod(mod) ;
			if(exponentBits.charAt(i) == '1' ) {
				res = res.multiply(base).mod(mod);
			}
		
		}
		return res;
	}
	
	private static BigInteger getRandomNum(BigInteger upper_limit, Random r) {
		BigInteger result = new BigInteger(upper_limit.bitLength(), r);
		if(result.compareTo(upper_limit) >= 0) {
			result = new BigInteger(upper_limit.bitLength(), r);
		}
		return result;
	}
}
