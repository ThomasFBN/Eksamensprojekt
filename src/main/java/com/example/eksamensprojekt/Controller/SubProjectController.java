package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Service.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;

@Controller
public class SubProjectController {
    private SubProjectService subProjectService;

   public SubProjectController(SubProjectService subProjectService){
       this.subProjectService = subProjectService;
   }





}
