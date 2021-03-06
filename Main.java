import java.math.BigInteger;
import java.util.Random;

public class Main {
	private static final int BITLENGTH = 2048;
	private static final int BITLENGTH_M = 29;
	public static void main(String[] args) {
		Random r = new Random();
		BigInteger m_size = BigInteger.probablePrime(BITLENGTH_M,r);
		BigInteger p = BigInteger.probablePrime(BITLENGTH_M,r);
        BigInteger a = getGcdOneRandom(p.subtract(BigInteger.ONE), r);

        BigInteger d = getRandomNum(p.subtract(BigInteger.valueOf(2)), r);
        BigInteger B = myPow(a,d,p);
        System.out.println("Bob");
        System.out.println("p: " + p);
        System.out.println("a: " + a);
        System.out.println("d: " + d);
        System.out.println("B: " + B);
        
        // Alice generating her keys and encrypting the message
        BigInteger i = getRandomNum(p, r);
        BigInteger ke = myPow(a,i,p);
        //BigInteger ke = getGcdOneRandom(a,i,p,r);
        BigInteger km = myPow(B,i,p);
//        BigInteger x = getRandomNum(m_size, r); // generating a message
        BigInteger x = getRandomNum(p, r); // generating a message
        System.out.println("Original message: " + x);
        x = myPow(a,x,p);
        System.out.println("Encrypted message using exponential el-gamal: " + x);
        BigInteger y = x.multiply(km).mod(p);
        System.out.println("\nAlice");
        System.out.println("i: " + i);
        System.out.println("ke: " + ke);
        System.out.println("km: " + km);
        System.out.println("y: " + y);       
        
        // Bob decrypting
        BigInteger km2 = myPow(ke,d,p);
        BigInteger x2 = y.multiply(km2.modInverse(p)).mod(p);
        BSGS test = new BSGS(x2, a, p);
        // now need to factor x2
        x2 = test.calculateExponent1();
        //x2 = factorMessage(x2,a,p);
        System.out.println("Decrypted message: " + x2);
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
		if(result.compareTo(upper_limit) >= 0 || result.compareTo(BigInteger.ONE) <= 0) {
			result = new BigInteger(upper_limit.bitLength(), r);
		}
		return result;
	}
	
	private static BigInteger getGcdOneRandom(BigInteger mod, Random r) {
		BigInteger result = new BigInteger(mod.bitLength(), r);
		if(result.compareTo(mod) >= 0 || result.compareTo(BigInteger.ONE) <= 0 || !result.gcd(mod).equals(BigInteger.ONE)) {
			result = new BigInteger(mod.bitLength(), r);
		}
		return result;
	}
	
	private static BigInteger factorMessage(BigInteger enc_mes, BigInteger eph_key, BigInteger p) {
		BigInteger return_val = BigInteger.ONE;
		BigInteger val = eph_key;
		boolean found = false;
		while(true) {
			return_val = return_val.add(BigInteger.ONE);
			//System.out.println("val before: " + val);
			val = val.multiply(eph_key).mod(p);
			//System.out.println("val after: " + val.mod(p));
			if(enc_mes.equals(val)) {
				found = true;
				break;
			}  
		}
		if(found) { return return_val; }
		else {
			System.out.println("Could not find the message.");
			return BigInteger.valueOf(-1);
		}
	}
}
