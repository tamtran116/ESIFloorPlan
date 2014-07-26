package edu.umsl.esi.floorplan.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import edu.umsl.esi.floorplan.domain.Cube;
import edu.umsl.esi.floorplan.domain.FileUpload;
import edu.umsl.esi.floorplan.domain.FloorEntity;
import edu.umsl.esi.floorplan.domain.Request;
import edu.umsl.esi.floorplan.services.CubeService;
import edu.umsl.esi.floorplan.services.FloorService;
import edu.umsl.esi.floorplan.services.RequestService;

@Controller
public class FloorplanController {
	
	@Autowired
    private CubeService cubeService;
	
	@Autowired
	private FloorService floorService;
	
	@Autowired
	private RequestService requesService;
	
	private Set<String> roles;
	private String ERROR = "you don't have enough power to do this, please practice more";
	private String floorUrl;
	private int floorId;
	private Cube currentCube;
	private Cube updateCube;
	
	
	 @RequestMapping(value = "/incorrectPage")
     public ModelAndView incorrectPage() {
		 roles = AuthorityUtils
	                .authorityListToSet(SecurityContextHolder.getContext()
	                        .getAuthentication().getAuthorities());
		 ModelAndView mov = new ModelAndView("incorrectPage");
		 mov.addObject("roles",roles);
         return mov; // logical view name
      }
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage(Map<String, Object> map) {
		System.out.println("Going to home page");
		String text = "98376373783"; // this is the text that we want to encode

		int width = 400;
		int height = 300; // change the height and width as per your requirement

		// (ImageIO.getWriterFormatNames() returns a list of supported formats)
		String imageFormat = "png"; // could be "gif", "tiff", "jpeg" 

		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(new File("/Users/tamtran/Documents/workspace-sts-3.4.0.RELEASE/ESIFloorPlan/WebContent/WEB-INF/qrcode_97802017507991.png")));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return new ModelAndView("home");
    }
	
    @RequestMapping(value="/floor" , method=RequestMethod.GET)
    public String getUploadForm( @RequestParam int floorId, Map<String, Object> map ) {
   	 this.floorUrl = floorService.getFloorInfo(floorId).getFilePath();
   	 this.floorId = floorId;
   	 System.out.println("floorUrl " + floorUrl);
   	 return "redirect:/list";
    }
      
      
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView showMap(Map<String, Object> map){
		ModelAndView mov = new ModelAndView("floor");
    	roles = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities());
    	if (currentCube != null) {
    		mov.addObject("currentCube",currentCube);
    	};
    	if (updateCube != null) {
    		mov.addObject("updateCube",updateCube);
    	};
    	mov.addObject("role",roles);
    	mov.addObject("request", new Request());
    	if (requesService.listRequest() !=null) {
    		mov.addObject("requestList", requesService.listRequest());
    	};
    	if (!floorUrl.isEmpty()) {
    		mov.addObject("floorUrl",floorUrl);
    		mov.addObject("cubeList", cubeService.listCubeByFloorId(floorId));
    		mov.addObject("floorId",floorId);
    	}
		mov.addObject("cube", new Cube());
		
		return mov;
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView error(Map<String, Object> map){
		ModelAndView mov = new ModelAndView("floor");
    	roles = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities());
    	mov.addObject("error",ERROR);
    	mov.addObject("role",roles);
		mov.addObject("cube", new Cube());
		mov.addObject("currentCube", new Cube());
		mov.addObject("updateCube", new Cube());
		mov.addObject("cubeList", cubeService.listCube());
		if (currentCube != null) {
    		mov.addObject("currentCube",currentCube);
    	};
    	if (updateCube != null) {
    		mov.addObject("updateCube",updateCube);
    	};
		return mov;
	}
    
	@RequestMapping(value = "/admin", method=RequestMethod.GET)
	public String adminPage(Map<String, Object> map) {
		return "redirect:/uploadfloor";
	};
	
	@RequestMapping(value = "/manager", method=RequestMethod.GET)
	public String managerPage(Map<String, Object> map) {
		return "redirect:/uploadfloor";
	};
	
	@RequestMapping(value = "/user", method=RequestMethod.GET)
	public String userPage(Map<String, Object> map) {
		return "redirect:/uploadfloor";
	};
	
	@RequestMapping(value = "/addCube", method = RequestMethod.POST)
    public String addCube(@ModelAttribute("cube") @Valid Cube cube, BindingResult result, Model m) {
		roles = getRoles();
		if (roles.contains("ROLE_ADMIN")) {
			cube.setFloor(floorService.getFloorInfo(floorId));
			cubeService.addCube(cube);
			return "redirect:/list";
		} else {
			return "redirect:/error";
		}
    };
    
    @RequestMapping(value = "/addRequest", method = RequestMethod.POST)
    public String addCube(@ModelAttribute("request") @Valid Request request, BindingResult result, Model m) {
		roles = getRoles();
		if (roles.contains("ROLE_USER")) {	
			requesService.addRequest(request);
			return "redirect:/list";
		} else {
			return "redirect:/error";
		}
    };
	
    @RequestMapping("/cube/{cube_id}")
    public String getCube(@PathVariable("cube_id") String cube_id) {
    	currentCube = cubeService.getCubeInfo(cube_id);
    	return "redirect:/list";
    }
    
    @RequestMapping("/json/{cube_id}")
    @ResponseBody
    public Cube getCubeJson(@PathVariable("cube_id") String cube_id) {
    	currentCube = cubeService.getCubeInfo(cube_id);
    	return currentCube;
    }
    
    @RequestMapping("/listcubejson")
    @ResponseBody
    public List<Cube> listCubeJson() {
    	List<Cube> cubeList = cubeService.listCube();
    	return cubeList;
    }
    
    @RequestMapping("/listfloorjson")
    @ResponseBody
    public List<FloorEntity> listFloorJson() {
    	List<FloorEntity> floorList = floorService.listFloor();
    	return floorList;
    }
    
    //using request param
    @RequestMapping(value="/updateCube", method=RequestMethod.GET)
    public String edit(@RequestParam("id")String id) {
    	updateCube = cubeService.getCubeInfo(id);
    	System.out.println("Get cube for update , cube id = " + id);
    	System.out.println("Cube from view "+currentCube);
    	return "redirect:/list";
    }
    
    @RequestMapping(value = "/updateCube", method = RequestMethod.POST )
    public String updateCube(@ModelAttribute("updateCube") @Valid Cube updateCube) {
		roles = getRoles();
		if (roles.contains("ROLE_ADMIN") || roles.contains("ROLE_MANAGER")) {
			cubeService.updateCube(updateCube);
			return "redirect:/list";
		} else {
			return "redirect:/error";
		}
    };
    
    @RequestMapping(value = "/swap", method = RequestMethod.GET )
    public String updateCube(@RequestParam("swap-one")String cube1_id, @RequestParam("swap-two")String cube2_id) {
		Cube cube1 = cubeService.getCubeInfo(cube1_id);
		Cube cube2 = cubeService.getCubeInfo(cube2_id);
		System.out.println("before swap");
		System.out.println(cube1);
		System.out.println(cube2);
		
		String nswap1 = cube2.getEmployee_name();
		String nswap2 = cube1.getEmployee_name();
		Boolean oswap1 = cube2.getOccupied();
		Boolean oswap2 = cube1.getOccupied();
		String tswap1 = cube2.getTeam_leader();
		String tswap2 = cube1.getTeam_leader();
		
		cube1.setEmployee_name(nswap1);
		cube2.setEmployee_name(nswap2);
		cube1.setOccupied(oswap1);
		cube2.setOccupied(oswap2);
		cube1.setTeam_leader(tswap1);
		cube2.setTeam_leader(tswap2);
		
		cubeService.updateCube(cube1);
		cubeService.updateCube(cube2);
		System.out.println("after swap");
		System.out.println(cube1);
		System.out.println(cube2);
		return "redirect:/list";
    };
    
	@RequestMapping("/deleteCube/{cube_id}")
    public String deleteCube(@PathVariable("cube_id") String cube_id) {
		roles = getRoles();
		if (roles.contains("ROLE_ADMIN")) {	
			cubeService.removeCube(cube_id);
		} else {
			return "redirect:/error";
		};
        return "redirect:/list";
    };
    
	@RequestMapping("/{team_leader}")
    public String getCubeByTeam(@PathVariable("team_leader") String team_leader, Map<String, Object> map) {
		map.put("cube", new Cube());
    	map.put("cubeList",cubeService.listCubesByTeam(team_leader));
    	return "floor";
    };
	
	@RequestMapping("/{team_leader}/{c_choice}")
    public String getClosest(@PathVariable("team_leader") String team_leader,@PathVariable("c_choice") int c_choice, Map<String, Object> map) {
		map.put("cube", new Cube());
    	map.put("cubeList",cubeService.getClosest(team_leader,c_choice));
    	return "floor";
    };
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Map<String, Object> map) {
        return "login";
    };
 
    @RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
    public String loginerror(Map<String, Object> map) {
        map.put("error", "true");
        return "denied";
    };
 
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Map map) {
        return "logout";
    };
    
    public Set<String> getRoles() {
    	roles = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities());
    	return roles;
    }
}
