package com.talentsprint.cycleshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.talentsprint.cycleshop.business.LoggedInUser;
import com.talentsprint.cycleshop.business.NeedsAuth;
import com.talentsprint.cycleshop.entity.Cycle;
import com.talentsprint.cycleshop.service.CycleService;


@RestController
@RequestMapping("/api/cycle")
public class CycleController {

    @Autowired
    private CycleService cycleService;

    @Autowired
    private LoggedInUser loggedInUser;
    
    @NeedsAuth(loginPage = "/loginpage")
    @GetMapping("/{id}/borrow")
    public ResponseEntity<String> borrowCycle(@PathVariable long id, @RequestParam(required=false, defaultValue="1") int count) {
        cycleService.borrowCycle(id, count);
        return ResponseEntity.ok("Cycle borrowed successfully.");
    }
    

   @NeedsAuth(loginPage = "/loginpage")
    @GetMapping("/{id}/return")
   
    public ResponseEntity<String> returnCycle(@PathVariable long id, @RequestParam(required=false, defaultValue="1") int count) {
        cycleService.returnCycle(id, count);
        return ResponseEntity.ok("Cycle returned successfully.");
    }

    @GetMapping("/{id}/restock")
    public ResponseEntity<String> restockCycle(@PathVariable long id, @RequestParam(required=false, defaultValue="1") int count) {
        if (this.loggedInUser.getLoggedInUser() == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        cycleService.restockBy(id, count);
        return ResponseEntity.ok("Cycle restocked successfully.");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Cycle>> listAvailableCycles() {
        List<Cycle> allCycles = cycleService.listAvailableCycles();
        return ResponseEntity.ok(allCycles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cycle> cycleDetail(@PathVariable long id) {
        Cycle cycle = cycleService.findByIdOrThrow404(id);
        return ResponseEntity.ok(cycle);
    }
    
//     /*
//      * For example, /1/borrow?count=3 borrows 3 cycles of id 1.
//      */
//    // @NeedsAuth(loginPage = "/loginpage")
//      @GetMapping("/{id}/borrow")
//     public String borrowCycle(@PathVariable long id, @RequestParam(required=false, defaultValue="1") int count) {
//         cycleService.borrowCycle(id, count);
//         //just a comment
//         //return ResponseEntity.ok("Cycle borrowed successfully.");
//         return "redirect:/api/cycle/list"; //TODO: redirect to List handler
//     }

//     //@NeedsAuth(loginPage = "/loginpage")
//     @GetMapping("/{id}/return")
//     public String returnCycle(@PathVariable long id, @RequestParam(required=false, defaultValue="1") int count) {
//         cycleService.returnCycle(id, count);
//         return "redirect:/api/cycle/list"; //TODO: redirect to list handler
//     }

//     @GetMapping("/{id}/restock")
//     public String restockCycle(@PathVariable long id, @RequestParam(required=false, defaultValue="1") int count) {
//         cycleService.restockBy(id, count);
//         return "redirect:/api/cycle/list";
//     }

//     @GetMapping("/list")
//     public String listAvailableCycles(Model model) {
//         // if (this.loggedInUser.getLoggedInUser() == null) {
//         //     return "redirect:/loginpage";
//         // }
//         var allCycles = cycleService.listAvailableCycles();
//         model.addAttribute("allCycles", allCycles);
//         return "cycleList";
//     }

//     @GetMapping("/{id}")
//     public String cycleDetail(@PathVariable long id, Model model) {
//         var cycle = cycleService.findByIdOrThrow404(id);
//         model.addAttribute("cycle", cycle);
//         return "cycleDetail";
//     }

}
