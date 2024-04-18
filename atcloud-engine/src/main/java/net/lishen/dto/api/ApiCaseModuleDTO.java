package net.lishen.dto.api;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author joseph,
 * @since 2024-03-06
 */
@Data
public class ApiCaseModuleDTO {



    private Long id;


    private Long projectId;


    private String name;


    private Date gmtCreate;


    private Date gmtModified;

    private List<ApiCaseDTO> list;
}
