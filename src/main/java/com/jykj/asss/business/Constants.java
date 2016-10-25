package com.jykj.asss.business;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**常用的常量**/
public class Constants {
	/**轮流报数**/
	public static void main(String[] args) {
		List<String> userList = getUser(5,2);
		for(String id : userList){
			System.out.println(id);
		}
	}
	
	private static List<String> getUser(int size,int num){
		LinkedList<String> userList = initList(size);
		List<String> tempList = new ArrayList<String>();
		for(int i=0;i<userList.size();i++){
			if(userList.size() == 1){
				tempList.add(userList.get(0));
			}else{
				for(int j=0;j<num;j++){
					String id = userList.getFirst();
					if(j==(num-1)){
						tempList.add(id);
					}
					userList.add(id);
					userList.removeFirst();
				}
			}
		}
		return tempList;
	}
	private static LinkedList<String> initList(int size){
		LinkedList<String> list = new LinkedList<String>();
		for(int i=0;i<size;i++){
			list.add(String.valueOf(i+1));
		}
		return list;
	}
	
}
