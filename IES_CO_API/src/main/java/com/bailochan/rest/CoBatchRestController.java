package com.bailochan.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bailochan.bindings.CoInfo;
import com.bailochan.entities.CoNoticeEntity;
import com.bailochan.service.CoService;

@RestController
@RequestMapping("/co")
public class CoBatchRestController {

    @Autowired
    private CoService service;
    
    
    @GetMapping("/{status}")
    public List<CoNoticeEntity> getNoticesByStatus(@PathVariable String status) {
        return service.getNoticesByStatus(status);
    }

    @PostMapping("/printNotice/{noticeId}")
    public String printNotice(@PathVariable Integer noticeId) {
        if (service.printNotice(noticeId)) {
            return "Notice printed successfully!";
        } else {
            return "Failed to print notice.";
        }
    }
}

