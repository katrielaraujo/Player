package br.imd.player.util;

public enum UserType {
	REGULAR(0),
	VIP(1);
	
	private final int value;

	UserType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
