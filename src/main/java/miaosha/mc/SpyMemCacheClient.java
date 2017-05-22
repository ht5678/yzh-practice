package miaosha.mc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;



/**
 * 
 * @author yuezh2   2017年5月22日 上午11:14:03
 *
 */
public class SpyMemCacheClient{

	private MemcachedClient client;
	public static final int DEFAULT_EXPIRE = 0;
	
	
	public SpyMemCacheClient(String serverPorts) throws IOException{
		String[] servers = serverPorts.split(",");
		List<InetSocketAddress> addresses = new ArrayList<InetSocketAddress>(servers.length);
		for(String server:servers){
			String[] serverPort = server.split(":");
			if(serverPort.length != 2){
				throw new IllegalArgumentException("invalid mc address: "+server);
			}
			int port = Integer.parseInt(serverPort[1]);
			addresses.add(new InetSocketAddress(serverPort[0],port));
		}
		this.client = new MemcachedClient(addresses);
	}

	public SpyMemCacheClient(InetSocketAddress... ia) throws IOException {
		this.client = new MemcachedClient(ia);
	}

	/**
	 * Get a memcache client over the specified memcached locations.
	 * 
	 * @param addrs
	 *            the socket addrs
	 * @throws IOException
	 *             if connections cannot be established
	 */
	public SpyMemCacheClient(List<InetSocketAddress> addrs) throws IOException {
		this.client = new MemcachedClient(addrs);
	}

	private static <T> T callFuture(Future<T> t, T defaultValue) {
		try {
			return t.get();
		} catch (InterruptedException e) {
			return defaultValue;
		} catch (ExecutionException e) {
			return defaultValue;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#set(java.lang
	 * .String, java.lang.Object)
	 */
	public boolean set(String key, Object value) {
		return callFuture(client.set(key, DEFAULT_EXPIRE, value), false);
	}
	
	public boolean cas(String key, long casId, Object value){
		CASResponse response = this.client.cas(key, casId, value);
		return response == CASResponse.OK || response == CASResponse.NOT_FOUND;
	}

	private static int dateToExpire(Date date) {
		return (int) (date.getTime() / 1000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#set(java.lang
	 * .String, java.lang.Object, java.util.Date)
	 */
	
	public boolean set(String key, Object value, Date date) {
		return callFuture(client.set(key, dateToExpire(date), value), false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#get(java.lang
	 * .String)
	 */
	
	public Object get(String key) {
		return this.client.get(key);
	}
	
	/**
	 * @see net.spy.memcached.MemcachedClient#gets(String)
	 * @param key
	 * @return
	 */
	public CASValue<Object> gets(String key){
		return this.client.gets(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#delete(java
	 * .lang.String)
	 */
	
	public boolean delete(String key) {
		return callFuture(this.client.delete(key), false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#getMulti(java
	 * .lang.String[])
	 */
	
	public Map<String, Object> getMulti(String[] keys) {
		return this.client.getBulk(keys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#add(java.lang
	 * .String, java.lang.Object)
	 */
	
	public boolean add(String key, Object value) {
		return callFuture(this.client.add(key, DEFAULT_EXPIRE, value), false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#add(java.lang
	 * .String, java.lang.Object, java.util.Date)
	 */
	
	public boolean add(String key, Object value, Date expdate) {
		return callFuture(this.client.add(key, dateToExpire(expdate), value),
				false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#incr(java.
	 * lang.String)
	 */
	
	public long incr(String key) {
		return this.client.incr(key, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#incr(java.
	 * lang.String, long)
	 */
	
	public long incr(String key, long inc) {
		return this.client.incr(key, inc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#decr(java.
	 * lang.String)
	 */
	
	public long decr(String key) {
		return this.client.decr(key, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.commons.memcached.MemcacheClient#decr(java.
	 * lang.String, int)
	 */
	
	public long decr(String key, int inc) {
		return this.client.decr(key, inc);
	}

	
	public Future<Boolean> setAsync(String key, Object value) {
		return this.client.set(key, DEFAULT_EXPIRE, value);
	}

	
	public Future<Boolean> setAsync(String key, Object value, Date expdate) {
		return this.client.set(key, dateToExpire(expdate), value);
	}

	
	public String toString(){
		return this.client.toString();
	}
}
