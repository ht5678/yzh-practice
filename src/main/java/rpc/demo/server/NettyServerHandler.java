package rpc.demo.server;

import java.lang.reflect.Method;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * 
 * @author sdwhy
 *
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RemoteRequest> {

	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext arg0, RemoteRequest arg1) throws Exception {
		Object serviceImpl = RemoteServer.getOnLineServiceImpl(arg1.getServiceName());
		
		//通过java的反射机制来调用接口中的具体方法
		if(serviceImpl!=null){
			Class<?> interfaceType = serviceImpl.getClass();
			Method method = interfaceType.getMethod(arg1.getMethodName(), arg1.getParameterTypes());
			
			Object result = method.invoke(serviceImpl, arg1.getArguments())
			Thread.sleep(3000);
			//构造response对象
			RemoteResponse remoteResponse = new RemoteResponse();
			remoteResponse.setRequestId(arg1.getRequestId());
			remoteResponse.setResponseValue(result);
			
			arg0.writeAndFlush(remoteResponse).addListener(ChannelFutureListener.CLOSE).addListener(arg0)
		}
		
	}

}
