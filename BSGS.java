

import java.math.BigInteger;
import java.util.HashMap;

public class BSGS {

	private BigInteger h;
	private BigInteger g;
	private BigInteger p;
	private BigInteger m; 

	
	public BSGS(BigInteger h, BigInteger g, BigInteger p) {
		this.h = h;
		this.g = g;
		this.p = p;
		this.m = sqrt(p);
	}

	/*
	 * BigInteger h= new BigInteger("696969");
	 * 
	 * BigInteger g= new BigInteger("21309"); BigInteger p= new
	 * BigInteger("999998999999");
	 */
	public BigInteger calculateExponent1() {
		HashMap<BigInteger, BigInteger> hm = new HashMap<BigInteger, BigInteger>();

		for (BigInteger i = BigInteger.ZERO; i.compareTo(m) == -1; i = i.add(BigInteger.ONE)) {
			hm.put(g.modPow(i, p), i);
//			System.out.println(g.modPow(i, p) + " " + i);

		}

		for (BigInteger i = BigInteger.ZERO; i.compareTo(m) == -1; i = i.add(BigInteger.ONE)) {
			BigInteger k = g.modPow(m.multiply(i).negate(), p).multiply(h).mod(p);
			BigInteger xb = hm.get(k);
			if (xb != null) {
				BigInteger exp = m.multiply(i).add(xb);
				System.out.println("Alg. 1: Exponent found with xb = " + xb + " : ");
				System.out.println(h + " = " + g + "^" + exp + " mod" + p);
				return exp;
			}
		}
		System.out.println("Exponent not found!!");
		return new BigInteger("-1");

	}

	public BigInteger calculateExponent2() {
		HashMap<BigInteger, BigInteger> hm = new HashMap<BigInteger, BigInteger>();

		BigInteger k;
		for (BigInteger i = BigInteger.ZERO; i.compareTo(m) == -1; i = i.add(BigInteger.ONE)) {
			hm.put(g.modPow(i, p), i);
//			System.out.println(g.modPow(i, p) + " " + i);
			k = g.modPow(m.multiply(i).negate(), p).multiply(h).mod(p);
//			System.out.println("Collision test: " + i + " with value " + k);
			BigInteger xb = hm.get(k);
			if (xb != null) {
				BigInteger exp = m.multiply(i).add(xb);
				System.out.println("Alg 2. Exponent found with xb = " + xb + " : ");
				System.out.println(h + " = " + g + "^" + exp + " mod" + p);
				return exp;
			}
		}
		System.out.println("Exponent not found!!");
		return new BigInteger("-1");

	}
	private BigInteger sqrt(BigInteger x) {

		BigInteger two = BigInteger.valueOf(2L);
		BigInteger y;
		// starting with y = x / 2 avoids magnitude issues with x squared
		for (y = x.divide(two); y.compareTo(x.divide(y)) > 0; y = ((x.divide(y)).add(y)).divide(two))
			;
		if (x.compareTo(y.multiply(y)) == 0) {
			return y;
		} else {
			return y.add(BigInteger.ONE);
		}

	}

}
