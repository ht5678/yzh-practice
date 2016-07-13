package zookeeper.zkclient.nameservice;

import zookeeper.zkclient.nameservice.IdMaker.RemoveMethod;

/**
 * 
 * @author sdwhy
 *
 */
public class TestIdMaker {
	
	
	public static void main(String[] args) throws Exception{
		IdMaker idMaker = new IdMaker("10.99.205.22:12181", "/NameService/IdGen", "ID");
		
		idMaker.start();
		
		try{
			for(int i = 0 ; i <10 ;i++){
				String id = idMaker.generateId(RemoveMethod.DELAY);
				System.out.println(id);
			}
		}finally{
			idMaker.stop();
		}
		
	}
	

}
