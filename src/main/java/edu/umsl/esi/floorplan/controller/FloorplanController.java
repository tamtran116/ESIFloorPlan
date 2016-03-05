package edu.umsl.esi.floorplan.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import edu.umsl.esi.floorplan.domain.*;
import edu.umsl.esi.floorplan.services.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/*import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;*/

@Controller
public class FloorplanController {
	
	@Autowired
    private CubeService cubeService;
	
	@Autowired
	private FloorService floorService;
	
	@Autowired
	private RequestService requesService;

	@Autowired
	private UserService userService;
	
	private Set<String> roles;
	private String ERROR = "you don't have enough power to do this, please practice more";
	private String floorUrl;
	private int floorId;
	private CubeDO currentCubeDO;
	private CubeDO updateCubeDO;
	
	
	 @RequestMapping(value = "/incorrectPage")
     public ModelAndView incorrectPage() {
		 roles = AuthorityUtils
	                .authorityListToSet(SecurityContextHolder.getContext()
	                        .getAuthentication().getAuthorities());
		 ModelAndView mov = new ModelAndView("incorrectPage");
		 mov.addObject("roles",roles);
         return mov; // logical view name
      }
	
    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.HEAD})
    public ModelAndView homePage() {
		System.out.println("Going to root context");
		ModelAndView mov = new ModelAndView("home");
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String text = "98376373783"; // this is the text that we want to encode
		int width = 400;
		int height = 300; // change the height and width as per your requirement
		// (ImageIO.getWriterFormatNames() returns a list of supported formats)
		String imageFormat = "png"; // could be "gif", "tiff", "jpeg"
		/*BitMatrix bitMatrix = null;
		try {
			bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		} catch (WriterException e) {
			e.printStackTrace();
		}*/
//		try {
//			MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(new File("/Users/tamtran/Documents/workspace-sts-3.4.0.RELEASE/ESIFloorPlan/WebContent/WEB-INF/qrcode_97802017507991.png")));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		if (!"anonymousUser".equals(username)) {
			mov.addObject("username", username);
		}
		return mov;
    }

    @RequestMapping(value="/floor" , method={RequestMethod.GET, RequestMethod.HEAD})
	 public String getUploadForm( @RequestParam int floorId, Map<String, Object> map ) {
		this.floorUrl = floorService.getFloorInfo(floorId).getFilePath();
		this.floorId = floorId;
		System.out.println("floorUrl " + floorUrl);
		return "redirect:list";
	}

	@RequestMapping(value="/deleteFloor" , method={RequestMethod.GET, RequestMethod.HEAD})
	public String deleteFloor( @RequestParam int floorId, Map<String, Object> map ) {
		floorService.removeFloor(floorId);
		return "redirect:uploadfloor";
	}
      
      
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.HEAD})
	public ModelAndView showMap(Map<String, Object> map){
		ModelAndView mov = new ModelAndView("floor");
    	roles = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities());
    	if (currentCubeDO != null) {
    		mov.addObject("currentCube", currentCubeDO);
    	}
    	if (updateCubeDO != null) {
    		mov.addObject("updateCube", updateCubeDO);
    	}
    	mov.addObject("role",roles);
    	mov.addObject("request", new RequestDO());
    	if (requesService.listRequest() !=null) {
    		mov.addObject("requestList", requesService.listRequest());
    	}
    	if (!StringUtils.isBlank(floorUrl)) {
    		mov.addObject("floorUrl",floorUrl);
    		mov.addObject("cubeList", cubeService.listCubeByFloorId(floorId));
    		mov.addObject("floorId",floorId);
    	}
		mov.addObject("cubeDO", new CubeDO());
		
		return mov;
	}
	
	@RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.HEAD})
	public ModelAndView error(Map<String, Object> map){
		ModelAndView mov = new ModelAndView("floor");
    	roles = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities());
    	mov.addObject("error",ERROR);
    	mov.addObject("role",roles);
		mov.addObject("cube", new CubeDO());
		mov.addObject("currentCube", new CubeDO());
		mov.addObject("updateCube", new CubeDO());
		mov.addObject("cubeList", cubeService.listCube());
		if (currentCubeDO != null) {
    		mov.addObject("currentCube", currentCubeDO);
    	};
    	if (updateCubeDO != null) {
    		mov.addObject("updateCube", updateCubeDO);
    	};
		return mov;
	}

	@RequestMapping(value = "/addCube", method = RequestMethod.POST)
    public String addCube(@ModelAttribute("cube") @Valid CubeDO cubeDO, BindingResult result, Model m) {
		roles = getRoles();
		if (roles.contains("ROLE_ADMIN")) {
			cubeDO.setFloor(floorService.getFloorInfo(floorId));
			cubeService.addCube(cubeDO);
			return "redirect:/list";
		} else {
			return "redirect:/error";
		}
    }
    
    @RequestMapping(value = "/addRequest", method = RequestMethod.POST)
    public String addCube(@ModelAttribute("request") @Valid RequestDO requestDO, BindingResult result, Model m) {
		roles = getRoles();
		if (roles.contains("ROLE_USER")) {	
			requesService.addRequest(requestDO);
			return "redirect:/list";
		} else {
			return "redirect:/error";
		}
    }
	
    @RequestMapping("/cube/{cube_id}")
    public String getCube(@PathVariable("cube_id") String cube_id) {
    	currentCubeDO = cubeService.getCubeInfo(cube_id);
    	return "redirect:/list";
    }
    
    @RequestMapping("/json/{cube_id}")
    @ResponseBody
    public CubeDO getCubeJson(@PathVariable("cube_id") String cube_id) {
    	currentCubeDO = cubeService.getCubeInfo(cube_id);
    	return currentCubeDO;
    }
    
    @RequestMapping("/listcubejson")
    @ResponseBody
    public List<CubeDO> listCubeJson() {
    	List<CubeDO> cubeDOList = cubeService.listCube();
    	return cubeDOList;
    }
    
    @RequestMapping("/listfloorjson")
    @ResponseBody
    public List<FloorDO> listFloorJson() {
    	List<FloorDO> floorList = floorService.listFloor();
    	return floorList;
    }
    
    //using request param
    @RequestMapping(value="/updateCube", method={RequestMethod.GET, RequestMethod.HEAD})
    public String edit(@RequestParam("id")String id) {
    	updateCubeDO = cubeService.getCubeInfo(id);
    	System.out.println("Get cube for update , cube id = " + id);
    	System.out.println("CubeDO from view "+ currentCubeDO);
    	return "redirect:/list";
    }
    
    @RequestMapping(value = "/updateCube", method = RequestMethod.POST )
    public String updateCube(@ModelAttribute("updateCube") @Valid CubeDO updateCubeDO) {
		roles = getRoles();
		if (roles.contains("ROLE_ADMIN") || roles.contains("ROLE_MANAGER")) {
			cubeService.updateCube(updateCubeDO);
			return "redirect:/list";
		} else {
			return "redirect:/error";
		}
    }
    
    @RequestMapping(value = "/swap", method = {RequestMethod.GET, RequestMethod.HEAD} )
    public String updateCube(@RequestParam("swap-one")String cube1_id, @RequestParam("swap-two")String cube2_id) {
		CubeDO cubeDO1 = cubeService.getCubeInfo(cube1_id);
		CubeDO cubeDO2 = cubeService.getCubeInfo(cube2_id);
		System.out.println("before swap");
		System.out.println(cubeDO1);
		System.out.println(cubeDO2);
		
		String nswap1 = cubeDO2.getEmployee_name();
		String nswap2 = cubeDO1.getEmployee_name();
		Boolean oswap1 = cubeDO2.getOccupied();
		Boolean oswap2 = cubeDO1.getOccupied();
		String tswap1 = cubeDO2.getTeamLeader();
		String tswap2 = cubeDO1.getTeamLeader();
		
		cubeDO1.setEmployee_name(nswap1);
		cubeDO2.setEmployee_name(nswap2);
		cubeDO1.setOccupied(oswap1);
		cubeDO2.setOccupied(oswap2);
		cubeDO1.setTeamLeader(tswap1);
		cubeDO2.setTeamLeader(tswap2);
		
		cubeService.updateCube(cubeDO1);
		cubeService.updateCube(cubeDO2);
		System.out.println("after swap");
		System.out.println(cubeDO1);
		System.out.println(cubeDO2);
		return "redirect:/list";
    }
    
	@RequestMapping("/deleteCube/{cube_id}")
    public String deleteCube(@PathVariable("cube_id") String cube_id) {
		roles = getRoles();
		if (roles.contains("ROLE_ADMIN")) {	
			cubeService.removeCube(cube_id);
		} else {
			return "redirect:/error";
		};
        return "redirect:/list";
    }
    
//	@RequestMapping("/{team_leader}")
//    public String getCubeByTeam(@PathVariable("team_leader") String team_leader, Map<String, Object> map) {
//		map.put("cube", new CubeDO());
//    	map.put("cubeList",cubeService.listCubesByTeam(team_leader));
//    	return "floor";
//    }
//
//	@RequestMapping("/{team_leader}/{c_choice}")
//    public String getClosest(@PathVariable("team_leader") String team_leader,@PathVariable("c_choice") int c_choice, Map<String, Object> map) {
//		map.put("cube", new CubeDO());
//    	map.put("cubeList",cubeService.getClosest(team_leader,c_choice));
//    	return "floor";
//    }


    public Set<String> getRoles() {
    	roles = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities());
    	return roles;
    }


}
