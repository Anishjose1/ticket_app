package com.wallmart.tiket.utils;
/***
 * Public constants
 * @author Anish Jose
 *
 */
public interface Constants {

	public static final String HOLD ="HOLD";
	public static final String STAGE = "----------[[ STAGE ]]------------";
	public static final String  FRONT_RAW= "---------------------------------";
	public static final char S ='s';
	public static final char R ='R';
	public static final char H ='H';
	public static final String FIND_SUM= "SELECT SUM(NUMBER_OF_SEATS) FROM BOOKING WHERE EVENT_ID = ? WHERE STATUS NOT IN('HOLD','RESERVED')";
	public static final String FIND_RESERVE= "SELECT SEATS,TIME FROM BOOKING WHERE EVENT_ID = ? AND STATUS = 'RESERVED'";
	public static final String FIND_HOLD = "SELECT SEATS,TIME FROM BOOKING WHERE EVENT_ID = ? AND STATUS = 'HOLD'";
	public static final String RESERVE_SEAT = "UPDATE BOOKING SET STATUS='RESERVED' WHERE EVENT_ID = ? AND TIME = ? AND EMAIL = ?";
	public static final String HOLD_SEAT = "SELECT TIME,EMAIL FROM BOOKING WHERE EVENT_ID = ? AND STATUS = 'HOLD'";
	public static final String SOFT_DELETE = "UPDATE BOOKING SET STATUS='FREE' WHERE EVENT_ID = ? AND TIME = ? AND EMAIL = ?";
}
