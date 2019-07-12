package com.allen.springcloud.ribbon;

import java.util.List;
import java.util.Random;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

public class RibbonDiyRule implements IRule {
	
	private ILoadBalancer lb;

	public Server choose(Object key) {
		//System.out.println("这是自定义的规则类");
		Random r = new Random();
		int randomNum = r.nextInt(10);
		List<Server> servers = lb.getAllServers();
		if(randomNum > 7) {
			Server s = getServerByPort(servers, 8081);
			return s;
		}
		return getServerByPort(servers, 8080);
	}
	
	private Server getServerByPort(List<Server> servers, int port) {
		for(Server s : servers) {
			if(s.getPort() == port) {
				return s;
			}
		}
		return null;
	}

	public void setLoadBalancer(ILoadBalancer lb) {
		this.lb = lb;
	}

	public ILoadBalancer getLoadBalancer() {
		return lb;
	}

}
