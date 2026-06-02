package com.system.modules.users.api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.system.crosscutting.domain.constants.ApiConstants;
import com.system.crosscutting.domain.model.EntyDeleteDto;
import com.system.crosscutting.domain.model.EntyRecmaesusuarimaResponse;
import com.system.crosscutting.domain.model.EntyUsersUtiliDto;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.users.usecase.EntyRecmaesusuarimaService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
public class EntyRecmaesusuarimaWebApi {

    @Autowired
    private EntyRecmaesusuarimaService service;

    @GetMapping("getall")
    @ApiOperation(httpMethod = ApiConstants.GET_HTTP, value = ApiConstants.GET_ALL_DESC, notes = "")
    public ResponseEntity<EntyRecmaesusuarimaResponse> getAll(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "parameter", required = false, defaultValue = "TEXT") String parameter,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAll(currentPage, pageSize, parameter, filter),
                HttpStatus.OK
        );
    }

    @GetMapping
    @ApiOperation(httpMethod = ApiConstants.GET_HTTP, value = ApiConstants.GET_ALL_DESC, notes = "")
    public ResponseEntity<EntyRecmaesusuarimaResponse> getAllBase(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "parameter", required = false, defaultValue = "TEXT") String parameter,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAll(currentPage, pageSize, parameter, filter),
                HttpStatus.OK
        );
    }

    @PostMapping("create")
    @ApiOperation(httpMethod = ApiConstants.POST_HTTP, value = ApiConstants.POST_DESC, notes = "")
    public ResponseEntity<EntyRecmaesusuarimaResponse> create(
            @RequestBody EntyRecmaesusuarimaResponse dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(service.saveBefore(dto), HttpStatus.CREATED);
    }

    @PutMapping("update")
    @ApiOperation(httpMethod = ApiConstants.PUT_HTTP, value = ApiConstants.PUT_DESC, notes = "")
    public ResponseEntity<EntyRecmaesusuarimaResponse> update(
            @RequestBody EntyRecmaesusuarimaResponse dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(service.updateAll(dto), HttpStatus.CREATED);
    }

    @PutMapping("updateimage")
    @ApiOperation(httpMethod = ApiConstants.PUT_HTTP, value = ApiConstants.PUT_DESC, notes = "")
    public ResponseEntity<EntyRecmaesusuarimaResponse> updateimage(
            @RequestBody EntyRecmaesusuarimaResponse dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(service.updateImage(dto), HttpStatus.CREATED);
    }

    @PatchMapping("changestatus")
    @ApiOperation(httpMethod = ApiConstants.PATCH_HTTP, value = ApiConstants.PATCH_DESC, notes = "")
    public String changestatus(
            @RequestBody List<EntyUsersUtiliDto> dto
    ) throws EBusinessException, MicroEventException {
        return service.changestatusAll(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation(httpMethod = ApiConstants.DELETE_HTTP, value = ApiConstants.DELETE_DESC, notes = "")
    public String delete(
            @RequestBody List<EntyDeleteDto> dto
    ) throws EBusinessException, MicroEventException {
        return service.deleteAll(dto);
    }
}