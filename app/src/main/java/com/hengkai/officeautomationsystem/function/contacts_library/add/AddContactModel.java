package com.hengkai.officeautomationsystem.function.contacts_library.add;

import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.AddGoodsSupplierService;
import com.hengkai.officeautomationsystem.network.service.ContactsLibraryService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 *
 */
public class AddContactModel {

    private final ContactsLibraryService service;

    public AddContactModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(ContactsLibraryService.class);
    }

    /**
     * 添加项目联系人
     *
     * @param name 名称
     * @param phone 电话
     * @param companyId 所属单位
     * @param birthday 生日
     * @param sex 性别
     * @param department 部门
     * @param rank 职位
     * @param weChat 微信
     * @param landline 座机
     * @param mailBox 电子邮箱
     * @param hobby 爱好
     * @param licensePlate 车牌号
     * @param observer
     */
    public void saveContact(String name, String phone, int companyId, String birthday, boolean sex,
                            String department, String rank, String weChat, String landline, String mailBox,
                            String hobby,  String licensePlate, Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("userId", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("name", name); // 名称
        params.put("phone", phone); // 电话
        params.put("companyId", companyId + ""); // 所属单位
        params.put("birthday", birthday); // 生日
        params.put("sex", String.valueOf(sex)); // 性别
        params.put("department", department); // 部门
        params.put("rank", rank); // 职位
        params.put("wechat", weChat); // 微信
        params.put("landline", landline); // 座机
        params.put("mailbox", mailBox); // 电子邮箱
        params.put("hobby", hobby); // 爱好
        params.put("licensePlate", licensePlate); // 车牌号

        service.saveContact(URLFinal.ADD_CONTACT_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
