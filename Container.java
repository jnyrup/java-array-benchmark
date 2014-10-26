final public class Container implements Comparable<Container> {
	final private String	_name;
	final private long		_time;

	Container(final long time, final String name) {
		_name = name;
		_time = time;
	}

	public long getTime() {
		return _time;
	}

	public String getName() {
		return _name;
	}

	@Override
	public int compareTo(Container other) {
		return (int) (_time - other.getTime());
	}

}
