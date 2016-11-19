package com.mkyong.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mkyong.web.domain.Tag;

@Controller
public class MainController {

    List<Tag> data = new ArrayList<Tag>();

    MainController() {
        // init data for testing
        data.add(new Tag(1, "ruby"));
        data.add(new Tag(2, "rails"));
        data.add(new Tag(3, "c / c++"));
        data.add(new Tag(4, ".net"));
        data.add(new Tag(5, "python"));
        data.add(new Tag(6, "java"));
        data.add(new Tag(7, "javascript"));
        data.add(new Tag(8, "jscript"));

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getPages() {

        ModelAndView model = new ModelAndView("example");
        return model;

    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public ModelAndView getMap() {

        ModelAndView model = new ModelAndView("dragAndDropGeoJSON");
        return model;

    }

    @RequestMapping(value = "/location", method = RequestMethod.GET)
    public ModelAndView getAjax() {

        ModelAndView model = new ModelAndView("ajax");
        return model;

    }

    @RequestMapping(value = "/complex", method = RequestMethod.GET)
    public ModelAndView getComplex() {

        ModelAndView model = new ModelAndView("complex");
        return model;

    }

    @RequestMapping(value = "/distance", method = RequestMethod.GET)
    public ModelAndView getDistance() {

        ModelAndView model = new ModelAndView("distance");
        return model;

    }

    @RequestMapping(value = "/draw", method = RequestMethod.GET)
    public ModelAndView getDraw() {

        ModelAndView model = new ModelAndView("draw");
        return model;

    }

    @RequestMapping(value = "/geometry", method = RequestMethod.GET)
    public ModelAndView getGeometry() {

        ModelAndView model = new ModelAndView("geometry");
        return model;

    }

    @RequestMapping(value = "/maplabel", method = RequestMethod.GET)
    public ModelAndView getMapLabel() {

        ModelAndView model = new ModelAndView("maplabel");
        return model;

    }

    @RequestMapping(value = "/animate", method = RequestMethod.GET)
    public ModelAndView getAnimate() {

        ModelAndView model = new ModelAndView("animate");
        return model;

    }

    @RequestMapping(value = "/direction", method = RequestMethod.GET)
    public ModelAndView getDirection() {

        ModelAndView model = new ModelAndView("direction");
        return model;

    }

    @RequestMapping(value = "/waypoint", method = RequestMethod.GET)
    public ModelAndView getWayPoint() {

        ModelAndView model = new ModelAndView("waypoint");
        return model;

    }


    @RequestMapping(value = "/getTags", method = RequestMethod.GET)
    public @ResponseBody
    List<Tag> getTags(@RequestParam String tagName) {

        return simulateSearchResult(tagName);

    }

    private List<Tag> simulateSearchResult(String tagName) {

        List<Tag> result = new ArrayList<Tag>();

        // iterate a list and filter by tagName
        for (Tag tag : data) {
            if (tag.getTagName().contains(tagName)) {
                result.add(tag);
            }
        }

        return result;
    }

}
