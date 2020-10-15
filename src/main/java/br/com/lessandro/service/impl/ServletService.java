package br.com.lessandro.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServletService {
	
	@Autowired
	HttpServletRequest req; 
	
    public String getApplicationURI() {
        try {
            String uri = req.getScheme().concat("://")
                    .concat(req.getServerName()).concat(":")
                    .concat(String.valueOf(req.getServerPort()))
                    .concat(req.getContextPath());
            return uri;
        } catch (Exception ex) {
        }
        return null;
    }
}
