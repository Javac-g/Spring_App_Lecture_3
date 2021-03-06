package com.company.MainVersion.Service;

import com.company.MainVersion.Controller.Car;
import com.company.MainVersion.Controller.RequestDTO;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private List<ResponseDTO> datalist = new ArrayList<>();

    public String print(String msg){
        return msg;
    }
    public void serviceLog(String type,ResponseDTO user){

        byte[] data = ("\nType: " + type + "\nName: " + user.getName() + "\nId: " + user.getId()).getBytes();
        String add = "\nTool - First: " + user.getTool().getFirst() + "\nTool - Second: " + user.getTool().getSecond() + "\nCar: " + user.getCarenum();

        try(FileOutputStream fileOutputStream = new FileOutputStream("log.dat",true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)){

            byteArrayOutputStream.write(data);
            byteArrayOutputStream.writeTo(fileOutputStream);
            dataOutputStream.writeUTF(add);

        }catch (IOException e){
            e.printStackTrace();
        }


    }
    public void setEnum(ResponseDTO user, RequestDTO json){
        for (Car car: json.getCarlist()){
            switch (car.getValue()){
                case "F":
                    user.setCarenum(CarEnum.Ford);
                    break;
                case "A":
                    user.setCarenum(CarEnum.Audi);
                    break;
                default:
                    user.setCarenum(CarEnum.Walk);
                    break;
            }
        }
    }

    public ResponseDTO create(RequestDTO json){
        ResponseDTO user = new ResponseDTO();
        user.setName(json.getName());
        user.setId(json.getId());
        user.setTool(json.getTool());
        setEnum(user,json);
        serviceLog(" - Created:",user);
        datalist.add(user);
        return user;
    }

    public ResponseDTO read(Integer id){
        for (ResponseDTO user:datalist){
            if (user.getId().equals(id)){
                serviceLog(" - Searched:",user);
                return user;
            }
        }
        return null;
    }

    public ResponseDTO update(Integer id, RequestDTO json){
        ResponseDTO user = read(id);
        if (user != null){
            user.setName(json.getName());
            user.setId(json.getId());
            user.setTool(json.getTool());
            setEnum(user,json);
            serviceLog(" - Updated:",user);
            return user;
        }


        return null;
    }
    public Integer Delete_A(Integer id){
        int x = -1;
        for (int i = 0; i < datalist.size(); i++){
            if (datalist.get(i).getId().equals(id)){
                x = i;
                serviceLog(" - Delete A:",datalist.get(x));
            }
        }
        if (x != -1){
            datalist.remove(x);
            return x;
        }
        return null;
    }

    public Integer Delete_B(Integer id){
        int x = -1;
        for (ResponseDTO user:datalist){
            if (user.getId().equals(id)){
               x = datalist.indexOf(user);
               serviceLog(" - Delete B:",user);
               datalist.remove(x);
               return x;
            }
        }
        return null;
    }

}//End.
