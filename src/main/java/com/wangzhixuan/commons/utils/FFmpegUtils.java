package com.wangzhixuan.commons.utils;
/**
 * Created by X201 on 2017/8/10.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FFmpegUtils {

    private final static String PATH = "需要转码的视频路径";

//    public static void main(String[] args) {
//        if (!checkfile(PATH)) {   //判断路径是不是一个文件
//            System.out.println(PATH + " is not file");
//            return;
//        }
//        if (processFLV(PATH)!="-1") {        //执行转码任务
//            System.out.println("ok");
//        }
//    }

    public static boolean checkfile(String path) {
        System.out.println("校验文件："+path);
        File file = new File(path);
        if (!file.isFile()) {
            System.out.println("文件不存在！");
            return false;
        }
        return true;
    }
    //判断文件类型
    public static int checkContentType(String path) {
        String type = path.substring(path.lastIndexOf(".") + 1, path.length())
                .toLowerCase();
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        }
        return 9;
    }

    // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）  该方法仅适用于 linux ffmpeg
    // 获取视频截图  返回截图路径
    public static String processFLV(String filepath,String pic_path) {

//            转码
//            Calendar c = Calendar.getInstance();
//            String savename = String.valueOf(c.getTimeInMillis())+ Math.round(Math.random() * 100000);
//            List<String> commend = new ArrayList<String>();
//            commend.add("D:\\ffmpeg\\ffmpeg");
//            commend.add("-i");
//            commend.add(filepath);
//            commend.add("-ab");
//            commend.add("56");
//            commend.add("-ar");
//            commend.add("22050");
//            commend.add("-qscale");
//            commend.add("8");
//            commend.add("-r");
//            commend.add("15");
//            commend.add("-s");
//            commend.add("600x500");
//            commend.add("【存放转码后视频的路径，记住一定是.flv后缀的文件名】");
//            调用线程命令进行转码
//            ProcessBuilder builder = new ProcessBuilder(commend);
//            builder.command(commend);
//            builder.start();
        try {
//            Runtime runtime = Runtime.getRuntime();
//            Process proce = null;
            //视频截图命令，封面图。  8是代表第8秒的时候截图
//                String cmd = "";
//                String cut = "     c:\\ffmpeg\\ffmpeg.exe   -i   "
//                        + filepath
//                        + "   -y   -f   image2   -ss   8   -t   0.001   -s   600x500   c:\\ffmpeg\\output\\"
//                        + "a.jpg";
//                String cutCmd = cmd + cut;
//                proce = runtime.exec(cutCmd);
            //            commend.add("D:\\ffmpeg\\ffmpeg");
            String imgName = UUID.randomUUID().toString() +".jpg";
            String imgPath = pic_path+imgName;

            //视频截图命令
            List<String> cutCommend = new ArrayList<String>();
            cutCommend.add("/usr/bin/ffmpeg");
            cutCommend.add("-ss");
            cutCommend.add("3");
            cutCommend.add("-i");
            cutCommend.add(filepath);
            cutCommend.add("-vframes");
            cutCommend.add("1");
            cutCommend.add(imgPath);
            System.out.println(cutCommend.toString());
            //调用线程命令进行截图
            ProcessBuilder builder = new ProcessBuilder(cutCommend);
            builder.command(cutCommend);
            builder.start();
            System.out.println("视频截图物理路径:"+imgPath );
            //只需返回图片名称
            return imgName ;
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

}
