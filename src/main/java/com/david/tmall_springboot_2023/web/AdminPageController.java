package com.david.tmall_springboot_2023.web;

import com.alibaba.fastjson.JSONObject;
import com.david.tmall_springboot_2023.pojo.*;
import com.david.tmall_springboot_2023.service.CategoryService;
import com.david.tmall_springboot_2023.service.ProductImageService;
import com.david.tmall_springboot_2023.service.ProductService;
import com.david.tmall_springboot_2023.service.PropertyService;
import com.david.tmall_springboot_2023.util.Page4Navigator;
import com.david.tmall_springboot_2023.util.RequestParameterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Controller
public class AdminPageController {

    @Autowired
    CategoryController categoryController;
    @Autowired
    CategoryService categoryService;
    @Autowired
    PropertyController propertyController;
    @Autowired
    PropertyService propertyService;
    @Autowired
    ProductController productController;
    @Autowired
    OrderController orderController;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;

    /**********************************   以下为 Category 相关操作   ***************************************************/

    /*
    查询全部分类
     */
    @GetMapping("/admin_category_list")
    public String listCategory(@RequestParam(value = "start", defaultValue = "0") int start,
                               @RequestParam(value = "size", defaultValue = "5") int size, Model model, HttpServletRequest request, HttpServletResponse response) {

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            System.out.println(header + " : " + request.getHeader(header));
        }

        Page4Navigator<Category> page4Navigator = categoryController.list(start, size);
        model.addAttribute("list", page4Navigator);

        return "admin/listCategory";
    }

    /*
    增加一个分类
     */
//    @PostMapping("/categories")
//    public String add(Category bean, @RequestParam(value="categoryPic", required = true) MultipartFile image, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        categoryService.add(bean);
//        System.out.println(request.getParameter("name"));
//        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
//        File file = new File(imageFolder, bean.getId()+".jpg");
//        image.transferTo(file);
////        ImageIO.write("jpg", file);
//        return "redirect:admin_category_list";
//    }

    /**
     * 增加一个分类
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/categories")
    public String add(Category category, @RequestParam(value = "categoryPic", required = true) MultipartFile image, HttpServletRequest request) throws IOException {
        categoryService.add(category);
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        image.transferTo(file);
        //为什么此次用 return redirect, 而不是用 return 页面？因为如果直接 return 页面， 那么页面数据需要重新获取， 所以可以直接跳转到
        // "redirect: admin_category_list" 来获取数据。
        return "redirect:admin_category_list";
    }


    /*
    修改分类时，得到一个分类， 然后跳转到分类编辑页面。
     */
    @GetMapping("admin_category_edit")
    public String editCategory(@RequestParam(value = "cid") int cid, Model model) {
        Category category = categoryService.findOne(cid);
        model.addAttribute("category", category);
        return "admin/editCategory";
    }

    /*
    修改分类后提交。
    通过 <form><input type="hidden" name="_method" value="PUT"></form> 来提交 put 请求。
    或者通过 Ajax 来提交。
     */
    @PutMapping("categories/{cid}")
    public ResponseEntity<String> editCategory(@RequestParam(value = "file", required = false) MultipartFile image, @PathVariable(value = "cid") int cid,
                                               HttpServletRequest request) throws IOException {
        String name = request.getParameter("filename");
        Category category = categoryService.findOne(cid);
        category.setName(name);
        categoryService.update(category);
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, cid + ".jpg");
        image.transferTo(file);
        return ResponseEntity.ok().build();
    }

    /*
    通过 Get 方法, 删除一个分类， 这也是常规操作。
     */
    @GetMapping("categories/{cid}")
    public String deleteCategory(@PathVariable(value = "cid") int cid, Model model, HttpServletRequest request) {
        categoryService.deleteOne(cid);
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, cid + ".jpg");
        file.delete();
        return "redirect:/admin_category_list";
    }

    /*
    删除时， 通过 ajax 来提交 delete 请求， 删除一个分类。
     */
    @DeleteMapping("categories/{cid}")
    public ResponseEntity<String> deleteCategory(@PathVariable(value = "cid") int cid, HttpServletRequest request) {
        System.out.println("cid: " + cid);
        categoryService.deleteOne(cid);
        System.out.println("delete Category invoked!");

        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, cid + ".jpg");
        file.delete();

        return ResponseEntity.ok().build();
    }

    /**********************************   以下为 Property 相关操作   ***************************************************/
    @GetMapping("admin_property_list/{cid}")
    public String listProperty(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size,
                               @PathVariable(value = "cid") int cid, Model model) {
        Page4Navigator<Property> properties = propertyController.list(start, size, cid);
        model.addAttribute("list", properties);
        model.addAttribute("cid", cid);
        return "admin/listProperty";
    }

    @PostMapping("properties")
    public String add(Property bean, HttpServletRequest request) {
        String name = request.getParameter("name");
        int cid = Integer.parseInt(request.getParameter("cid"));
        Property property = new Property();
        property.setCategory(categoryService.findOne(cid));
        property.setName(name);
        propertyService.add(property);

        return "redirect:/admin_property_list?cid=" + cid;
    }

    /*
    通过 Get 方法来做删除。
     */
    @GetMapping("admin_property_delete")
    public String delete(@RequestParam(value = "id") int id,
                         @RequestParam(value = "cid") int cid) {
        Property property = propertyService.get(id);
        propertyService.delete(property);
        return "redirect:/admin_property_list?cid=" + cid;
    }

    /*
    通过 Ajax 发送 Delete 请求来做删除。
     */
    @DeleteMapping("admin_property_delete")
    public ResponseEntity<String> deleteProperty(@RequestParam(value = "id") int id,
                                                 @RequestParam(value = "cid") int cid) {

        System.out.println("id = " + id);
        System.out.println("cid = " + cid);

        Property property = propertyService.get(id);
        propertyService.delete(property);
        return ResponseEntity.ok().build();
    }

    @GetMapping("admin_property_edit")
    public String get(@RequestParam(value = "id") int id, Model model) {
        Property property = propertyService.get(id);
        model.addAttribute("property", property);
        return "admin/editProperty";
    }

    @PutMapping("properties/{id}")
    public String editProperty(@PathVariable(value = "id") int id, @RequestParam(value = "name") String name) {
        Property property = propertyService.get(id);
        property.setName(name);

        System.out.println(" *****  PUT method invoked!  **** ");
        propertyService.edit(property);
        int cid = property.getCategory().getId();
        return "redirect:/admin_property_list?cid=" + cid;
    }

    /*********************************  以下为产品 Product 相关操作。 **************************************************/
    @GetMapping("admin_product_list/{cid}")
    public String listProducts(@RequestParam(value = "start", defaultValue = "0") int start,
                               @RequestParam(value = "size", defaultValue = "5") int size,
                               @PathVariable(value = "cid") int cid, Model model) {

        Page4Navigator<Product> products = productController.listProducts(start, size, cid);
        model.addAttribute("list", products);
        model.addAttribute("cid", cid);

        return "admin/listProduct";
    }

    @GetMapping("admin_order_list")
    public String listOrders(@RequestParam(value = "start", defaultValue = "0") int start,
                             Model model) {
        Page4Navigator<Order> page = orderController.list(start, 5);
        model.addAttribute("page", page);

        return "admin/listOrders";
    }


    /*********************************  以下为产品 ProductImage 相关操作。 **************************************************/
    @GetMapping("admin_productimage_list")
    public String listProductImages(@RequestParam(value = "pid") int pid, Model model) {
        Product product = productService.get(pid);

        List<ProductImage> singleProductImages = productImageService.findAllByProductAndType(product, ProductImageService.type_single);
        model.addAttribute("singles", singleProductImages);

        List<ProductImage> detailProductImages = productImageService.findAllByProductAndType(product, ProductImageService.type_detail);
        model.addAttribute("details", detailProductImages);

        model.addAttribute("pid", pid);

        return "admin/listProductImages";

    }

    @PostMapping("/productImages")
    public String add(@RequestParam(value = "image") MultipartFile image, @RequestParam(value = "type") String type,
                      @RequestParam(value = "pid") int pid, HttpServletRequest request) throws Exception {
        Product product = productService.get(pid);

        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setType(type);

        productImageService.add(productImage);

        File file = new File(request.getServletContext().getRealPath("/img/productSingle"));
        File picture = new File(file, productImage.getId() + ".jpg");

        image.transferTo(picture);

        return "redirect:admin_productimage_list?pid=" + pid;
    }

}




























