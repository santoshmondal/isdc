package com.isdc.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isdc.app.domain.AreaMaster;
import com.isdc.app.domain.BeatMaster;
import com.isdc.app.domain.MainAccount;
import com.isdc.app.domain.supplier.Supplier;
import com.isdc.app.global.GlobalFunctions;
import com.isdc.app.service.AreaMasterService;
import com.isdc.app.service.BeatMasterService;
import com.isdc.app.service.MainAccountService;
import com.isdc.app.service.SupplierService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/")
public class SupplierController {
	
	@Autowired
	@Resource(name="AreaMasterService")
	private AreaMasterService areaMasterService;
	
	@Autowired
	@Resource(name="BeatMasterService")
	private BeatMasterService beatMasteService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	@Resource(name="MainAccountService")
	private MainAccountService mainAccountService;
	
	@RequestMapping(value={"supplier/dashboard","supplier/dashboard/","supplier/","supplier"})
	public String getDashBoard(Model model){
		List<Supplier> supplierList = supplierService.getSupplierList();
		model.addAttribute("supplierList", supplierList);
		return "supplier/dashboard";
	}
	
	@RequestMapping(value={"supplier/add","supplier/add/"})
	public String addSupplierMaster(Model model){
		model.addAttribute("supplier", new Supplier());
		List<AreaMaster> areaMasterList = areaMasterService.getAll();
    	model.addAttribute("areaMasterList", areaMasterList); 
		return "supplier/add";
	}
	
	@RequestMapping(value={"supplier/edit-{supplierId}","supplier/edit-{supplierId}/"})
	public String editSupplierMaster(@PathVariable Long supplierId, Model model){
		Supplier supplier = supplierService.getSupplierById(supplierId);
		model.addAttribute("supplier", supplier); 
		
		List<AreaMaster> areaMasterList = areaMasterService.getAll();
    	model.addAttribute("areaMasterList", areaMasterList);
    	
    	List<BeatMaster> beatMasterList = null;
		beatMasterList = beatMasteService.getByArea( supplier.getArea().getArea_id() );
		model.addAttribute("beatMasterList", beatMasterList); 
		
		return "supplier/add";
	}
	
	@RequestMapping(value={"supplier/saveSupplierMaster"}, method=RequestMethod.POST)
	public String saveSupplierMaster(Supplier supplier, HttpServletRequest request){
		
		String areaId = request.getParameter("areaId");
		String beatId = request.getParameter("beatId");
		
		AreaMaster areaMaster = areaMasterService.getSingle( Integer.valueOf(areaId) );
		BeatMaster beatMaster = beatMasteService.getSingle( Integer.valueOf(beatId) );
		
		supplier.setArea(areaMaster);
		supplier.setBeat(beatMaster);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainAccount = mainAccountService.getByUsername(username);
		supplier.setUpdatedBy(mainAccount);
		Date date = new Date();
		supplier.setUpdatedDate(date);
		
		if( supplier.getSupplierId() == null ){		// ADD NEW
			username = GlobalFunctions.currentUserDetails().getUsername();
			mainAccount = mainAccountService.getByUsername(username);
			supplier.setCreatedBy(mainAccount);
			supplier.setCreatedDate(date);
			supplierService.addSupplier(supplier);
			
		}
		else{		// EDIT EXISTIG
			Supplier oldSupplier = supplierService.getSupplierById( supplier.getSupplierId() );
			supplier.setCreatedBy(oldSupplier.getCreatedBy());
			supplier.setCreatedDate(oldSupplier.getCreatedDate());
			supplierService.updateSupplier(supplier);
			return "redirect:dashboard?message=update-success";
		}
		
		return "redirect:dashboard?message=create-success";
	}
	
	
	// Get beat list on select of area
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"supplier/getBeatList"}, method=RequestMethod.POST)
	public void getBeatListByArea(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		String areaId = request.getParameter("areaId");
		
		if(areaId == null || areaId.trim().length() == 0){
			jsonObj.put("found", "NO");
		}
		List<BeatMaster> beatMasterList = null;
		try{
			beatMasterList = beatMasteService.getByArea( Integer.valueOf( areaId ) );
		}catch(Exception e){ e.printStackTrace();}
		
		StringBuilder result = new StringBuilder();
		if(beatMasterList != null && beatMasterList.size() > 0){
			jsonObj.put("found", "YES");
			result.append("<option value=\"\">--Select Beat--</option>");
			for( BeatMaster beatMaster : beatMasterList ){
				result.append("<option value=\""+beatMaster.getBeat_id()+"\">"+beatMaster.getBeat_name()+"</option>");
			}
			jsonObj.put("result", result.toString());
		}
		else{
			jsonObj.put("found", "NO");
			
		}
		out.write( jsonObj.toJSONString() );
		out.close();
	}
	
}
