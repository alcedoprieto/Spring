
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import model.conexion;
import model.usuarios;
import model.validarUsuarios;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("edit.htm")
public class editController 
{

    validarUsuarios usuariosValidar;
    private JdbcTemplate jdbcTemplate;
    
    public editController()
        {
        this.usuariosValidar=new validarUsuarios();
        conexion con = new conexion();
        this.jdbcTemplate = new JdbcTemplate(con.conectar());
        }
    @RequestMapping(method=RequestMethod.GET) 
    public ModelAndView form(HttpServletRequest request)
    {
        ModelAndView mav=new ModelAndView();
        int id=Integer.parseInt(request.getParameter("id"));
        usuarios datos=this.selectUsuario(id);
        mav.setViewName("edit");
        mav.addObject("usuarios",new usuarios(id,datos.getNombre(),datos.getCorreo(),datos.getTelefono()));
        return mav;
    }
    public usuarios selectUsuario(int id) 
    {
        final usuarios user = new usuarios();
        String sql = "SELECT * FROM usuarios WHERE id='" + id+"'";
        return (usuarios) jdbcTemplate.query
        (
                sql, new ResultSetExtractor<usuarios>() 
            {
                public usuarios extractData(ResultSet rs) throws SQLException, DataAccessException {
                    if (rs.next()) {
                        user.setNombre(rs.getString("nombre"));
                        user.setCorreo(rs.getString("correo"));
                        user.setTelefono(rs.getString("telefono"));
                    }
                    return user;
                }


            }
        );
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView form
        (
                @ModelAttribute("usuarios") usuarios u,
                BindingResult result,
                SessionStatus status,
                HttpServletRequest request
        )
    {
        this.usuariosValidar.validate(u, result);
        if(result.hasErrors())
        {
            ModelAndView mav=new ModelAndView();
            int id=Integer.parseInt(request.getParameter("id"));
            usuarios datos=this.selectUsuario(id);
            mav.setViewName("edit");
            mav.addObject("usuarios",new usuarios(id,datos.getNombre(),datos.getCorreo(),datos.getTelefono()));
            return mav;
        }else
        {
            int id=Integer.parseInt(request.getParameter("id"));
            this.jdbcTemplate.update(
                    "update usuarios "
                    + "set nombre=?,"
                    + " correo=?,"
                    + "telefono=? "
                    + "where "
                    + "id=? ",
             u.getNombre(),u.getCorreo(),u.getTelefono(),id);
             return new ModelAndView("redirect:/home.htm");
        }
       
    }
}
