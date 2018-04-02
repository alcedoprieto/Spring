
package controller;

import model.conexion;
import model.usuarios;
import model.validarUsuarios;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("add.htm")
public class addController 
{
    
    validarUsuarios usuariosValidar;
    private JdbcTemplate jdbcTemplate;
    
    public addController() 
    {
        this.usuariosValidar=new validarUsuarios();
        conexion con = new conexion();
        this.jdbcTemplate = new JdbcTemplate(con.conectar());
    }
    @RequestMapping(method=RequestMethod.GET) 
    public ModelAndView form()
    {
        ModelAndView mav=new ModelAndView();
        mav.setViewName("add");
        mav.addObject("usuarios",new usuarios());
        return mav;
    }
    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView form
        (
                @ModelAttribute("usuarios") usuarios u,
                BindingResult result,
                SessionStatus status
        )
    {
        this.usuariosValidar.validate(u, result);
        if(result.hasErrors())
        {
            ModelAndView mav=new ModelAndView();
            mav.setViewName("add");
            mav.addObject("usuarios",new usuarios());
            return mav;
        }else
        {
        this.jdbcTemplate.update
        (
        "insert into usuarios (nombre,correo,telefono ) values (?,?,?)",
         u.getNombre(),u.getCorreo(),u.getTelefono()
        );
         return new ModelAndView("redirect:/home.htm");
        }
       
    }
}
