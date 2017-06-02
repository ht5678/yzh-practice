package rpc.demo.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

/**
 * 
 * @author sdwhy
 *
 */
public class Serialize {

	
	/**
	 * hessian2序列化
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] serialize(Object obj)throws IOException{
		ByteArrayOutputStream byteArrayOutputStream = null;
		Hessian2Output hessian2Output = null;
		try{
			byteArrayOutputStream = new ByteArrayOutputStream();
			hessian2Output = new Hessian2Output(byteArrayOutputStream);
			hessian2Output.writeObject(obj);
		}finally{
			hessian2Output.close();
			byteArrayOutputStream.close();
		}
		return byteArrayOutputStream.toByteArray();
	} 
	
	
	/**
	 * hessian2反序列化
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static Object deserialize(byte[] bytes)throws IOException{
		ByteArrayInputStream byteArrayInputStream = null;
		Hessian2Input hessian2Input = null;
		Object object= null;
		try{
			byteArrayInputStream = new ByteArrayInputStream(bytes);
			hessian2Input = new Hessian2Input(byteArrayInputStream);
			object = hessian2Input.readObject();
		}finally{
			hessian2Input.close();
			byteArrayInputStream.close();
		}
		return object;
	}
	
	
}
