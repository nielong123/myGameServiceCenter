package com.org.myGameServiceCenter.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/** 
 * @ClassName LocalIPUtil  
 * @Description 获取IP地址 
 * @author dingyl 
 *    * @date 2016年1月21日  

 */
public class HostUtil {
	private static final Logger logger = LoggerFactory.getLogger(HostUtil.class);
	public static String localHost = getLocalHost();
	public static String hostName=getHostName();
	
	/**
	 * @Title: getLocalHost 
	 * @Description: 获取本机ip地址，并自动区分Windows还是linux操作系统
	 * @return 参数说明
	 * @return String    返回类型
	 */
	private static String getLocalHost() {
		String sIP = null;
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			} else {
				// 如果是Linux操作系统
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(" error ",e);
		}

		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}
	public static String getHostNameForLiunx() {
		try {
			return (InetAddress.getLocalHost()).getHostName();
		} catch (UnknownHostException uhe) {
			String host = uhe.getMessage(); // host = "hostname: hostname"
			if (host != null) {
				int colon = host.indexOf(':');
				if (colon > 0) {
					return host.substring(0, colon);
				}
			}
			return "UnknownHost";
		}
	}


	public static String getHostName() {
		if (System.getenv("COMPUTERNAME") != null) {
			return System.getenv("COMPUTERNAME");
		} else {
			return getHostNameForLiunx();
		}
	}
	/**
	 * 获得真实的ip
	 * @Title: getClientHost 
	 * @param request
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}
	public static void main(String[] args) {
		logger.info(HostUtil.hostName);
	}
}
