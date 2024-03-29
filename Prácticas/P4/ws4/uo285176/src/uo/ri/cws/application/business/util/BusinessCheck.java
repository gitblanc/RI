/**
 * 
 */
package uo.ri.cws.application.business.util;

import uo.ri.cws.application.business.BusinessException;

/**
 * @author UO285176 Todos los m�todos lanzar�n una BusinessException, que es una
 *         Excepci�n controlada
 *
 */
public class BusinessCheck {
	public static void isNotNull(final Object obj) throws BusinessException {
		isTrue(obj != null, " Cannot be null ");
	}

	public static void isNotNull(final Object obj, String msg) throws BusinessException {
		isTrue(obj != null, msg);
	}

	public static void isNull(final Object obj) throws BusinessException {
		isTrue(obj == null, " Must be null ");
	}

	public static void isTrue(final boolean test) throws BusinessException {
		isTrue(test, "Condition must be true");
	}

	public static void isTrue(final boolean test, final String msg) throws BusinessException {
		if (test == true) {
			return;
		}
		throwException(msg);
	}

	public static void isNotEmpty(final String str) throws BusinessException {
		isTrue(str != null, "The string cannot be null not empty");

		isTrue(str.trim().length() > 0, "The string cannot be null not empty");
	}

	public static void isNotEmpty(final String str, final String msg) throws BusinessException {
		isTrue(str != null, msg);
		isTrue(str.trim().length() > 0, msg);
	}

	protected static void throwException(final String msg) throws BusinessException {
		throw new BusinessException(msg);
	}
}
