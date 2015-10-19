
public enum Opcodes {
	
	BROADCAST_POSITION_UPDATE(1),
	POSITION_UPDATE(2);
	
	private int _code;
	
	private Opcodes(int code) 
	{
		this._code = code;
	}
	
	public int getCode()
	{
		return _code;
	}

}
