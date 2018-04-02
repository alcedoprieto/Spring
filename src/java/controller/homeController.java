package controller;

import java.util.List;
import model.conexion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class homeController 
{
    
    private JdbcTemplate jdbcTemplate;
    
    public homeController()
    {
        conexion con = new conexion();
        this.jdbcTemplate = new JdbcTemplate(con.conectar());
    }
    
    @RequestMapping("home.htm")
    public ModelAndView home()
    {
        ModelAndView mav = new ModelAndView();
        
        String sql ="select * from usuarios";
        List datos = this.jdbcTemplate.queryForList(sql);
        mav.addObject("datos",datos);
        mav.setViewName("home");
        return mav;
    }

    
}
