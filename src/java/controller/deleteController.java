
package controller;

import javax.servlet.http.HttpServletRequest;
import model.conexion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


public class deleteController 
{
    
    private JdbcTemplate jdbcTemplate;
    
    public deleteController()
    {
        conexion con = new conexion();
        this.jdbcTemplate = new JdbcTemplate(con.conectar());
    }
    
    @RequestMapping("delete.htm")
    public ModelAndView home(HttpServletRequest request) 
    {
        int id=Integer.parseInt(request.getParameter("id"));
        this.jdbcTemplate.update(
                "delete from usuarios "
                + "where "
                + "id=? ",
        id);
        return new ModelAndView("redirect:/home.htm");
    }
    
}
