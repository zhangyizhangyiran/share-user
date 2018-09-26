/*
 * Copyright (C) 2012 Chengel_HaltuD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soe.sharesoe.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: X
 * @Description: VerifyCheck 描述：验证类
 * @Author：Chengel_HaltuD
 * @Date：2015-8-29 下午2:26:17
 * @version V1.0
 *
 */
public class X
{


	private static int[] idsArray = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/**
	 * 
	 * @Title: isCarBrand 
	 * @Description: 验证车牌号 
	 * @param carBrand
	 * @return
	 * @return boolean
	 *
	 */
	public static boolean isCarBrand(String carBrand)
	{
		if ((carBrand == null) || ("".equals(carBrand.trim()))) {
			return false;
		}
		Pattern p = Pattern.compile("^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{4}[a-zA-Z_0-9_\u4e00-\u9fa5]$");
		Matcher m = p.matcher(carBrand);
		return m.matches();
	}

	/**
	 * 账号是否验证通过
	 * 支持2-16位英文�?中文、数字�?下划线�?�?
	 * @return
	 */
	public static boolean isAccountVerify(String account){
		
		if(account == null || "".equals(account.trim())) {
			return false;
		}else{
			String accountTrim = account.trim();
			Pattern patternAccount = Pattern.compile("^([a-zA-Z0-9_.\u4e00-\u9fa5]{2,16})+$");
			Matcher matcherAccount = patternAccount.matcher(accountTrim);
			if (!matcherAccount.matches()) {
				return false;
			}else{
				return true;
			}
		}
	}

	/**
	 * 验证手机号码的格式是否正�?
	 * @param mobileString
	 * @return
	 */
	public static  boolean isMobilePhoneVerify(String mobileString){
		if (mobileString == null || "".equals(mobileString.trim())) {//如果是null或""直接返回
			return false;
		}else{
			String mobileTrim = mobileString.trim();//去空格
			Pattern patternMobile = Pattern.compile("^1[3|5|4|7|8][0-9]{9}$");//正则表达式
			Matcher matcherMobile = patternMobile.matcher(mobileTrim);
			if(!matcherMobile.matches()){//true means match 
				return false;
			}else{
				return true;
			}
		}
	}

	/**
	 * 验证手机号码的格式是否正�?
	 * @param
	 * @return
	 */
	public static boolean isMobileNo(String mobile)
	{
		if (TextUtils.isEmpty(mobile)) {
			return false;
		}
		Pattern p = Pattern.compile("1[3|4|5|6|8|9]\\d{9}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 验证邮箱格式是否正确
	 * @param emailString
	 * @return
	 */
	public static boolean isEmailVerify(String emailString){
		if(emailString == null || "".equals(emailString.trim())){
			return false;
		}else{
			String emailTrim = emailString.trim();
			Pattern patternEmail = Pattern.compile("^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+$");
			Matcher matcherEmail = patternEmail.matcher(emailTrim);
			if (!matcherEmail.matches()) {
				return false;
			}else{
				return true;
			}
		}
	}

	/**
	 * 验证真是姓名的格式是否正确，是否是中�?
	 * @param realitynameString
	 * @return
	 */
	public static boolean isRealnameVerify(String realitynameString){
		if (realitynameString == null || "".equals(realitynameString.trim())) {
			return false;
		}else{
			String realitynameTrim = realitynameString.trim();
			Pattern patternRealityname = Pattern.compile("[\u4e00-\u9fa5]{2,10}");// 2~10个中文汉�?
			Matcher matcherRealityname = patternRealityname.matcher(realitynameTrim);
			if (!matcherRealityname.matches()) {
				return false;
			}else{
				return true;
			}
		}
	}

	/**
	 * 验证密码的强度
	 *  1.如果密码少于5位,那么就认为这是一个弱密码.
	 *	2.如果密码只由数字、小写字母、大写字母或其它特殊符号当中的一种组成,则认为这是一个弱密码.
	 *	3.如果密码由数字、小写字母、大写字母或其它特殊符号当中的两种组成,则认为这是一个中度安全的密码.
	 *	4.如果密码由数字、小写字母、大写字母或其它特殊符号当中的三种以上组成,则认为这是一个比较安全的密码.
	 * @return
	 */
	public static String judgePassLevel(String string){
		String str1 = "弱";
		String str2 = "中";
		String str3 = "强";
		if(string.length()<5){
			return str1;
		}

		int num = judgePassNum(string);
		if(num <= 0||num == 1){
			return str1;
		}else if(num == 2){
			return str2;
		}else{
			return str3;
		}

	}



	public static int judgePassNum(String string){
		String str1 = "^[0-9]$";
		String str2 = "^[a-z]$";
		String str3 = "^[A-Z]$";
		String str4 = "^[^0-9a-zA-Z]$";
		Pattern pattern1 = Pattern.compile(str1);
		Pattern pattern2 = Pattern.compile(str2);
		Pattern pattern3 = Pattern.compile(str3);
		Pattern pattern4 = Pattern.compile(str4);

		List<Integer> list = new ArrayList<Integer>();

		String[] split = string.split("");
		for (int i = 1; i < split.length; i++) {
			if(pattern1.matcher(split[i]).matches()){
				if(!list.contains(1)){
					list.add(1);
				}
			}
			if(pattern2.matcher(split[i]).matches()){
				if(!list.contains(2)){
					list.add(2);
				}
			}
			if(pattern3.matcher(split[i]).matches()){
				if(!list.contains(3)){
					list.add(3);
				}
			}
			if(pattern4.matcher(split[i]).matches()){
				if(!list.contains(4)){
					list.add(4);
				}
			}
		}
		return list.size();
	}

	// 判断身份证号码是否有效
	public static boolean isValidIdCard(String idCard) {
		return IdcardValidator.isValidateIdcard(idCard);
	}

	// 校验身份证的基本组成
	public boolean isIdcard(String idCard) {
		if (!TextUtils.isEmpty(idCard)) {
			String regex = "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)";
			return Pattern.matches(regex, idCard);
		}

		return false;
	}

	// 校验15身份证的基本组成
	public boolean is15Idcard(String idCard) {
		if (!TextUtils.isEmpty(idCard)) {
			String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
			return Pattern.matches(regex, idCard);
		}

		return false;
	}

	// 校验18身份证的基本组成
	public boolean is18Idcard(String idCard) {
		if (!TextUtils.isEmpty(idCard)) {
			String regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";
			return Pattern.matches(regex, idCard);
		}

		return false;
	}

	/**
	 * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
	 *
	 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
	 *
	 * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
	 *
	 * 第十八位数字(校验码)的计算方法为：
	 *      1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
	 *      2.将这17位数字和系数相乘的结果相加。
	 *      3.用加出来和除以11，看余数是多少？
	 *      4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为: 1 0 X 9 8 7 6 5 4 3 2;
	 *      5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
	 */
	private static class IdcardValidator {

		// 省,直辖市代码表
		private static final String codeAndCity[][] = { { "11", "北京" }, { "12", "天津" },
				{ "13", "河北" }, { "14", "山西" }, { "15", "内蒙古" }, { "21", "辽宁" },
				{ "22", "吉林" }, { "23", "黑龙江" }, { "31", "上海" }, { "32", "江苏" },
				{ "33", "浙江" }, { "34", "安徽" }, { "35", "福建" }, { "36", "江西" },
				{ "37", "山东" }, { "41", "河南" }, { "42", "湖北" }, { "43", "湖南" },
				{ "44", "广东" }, { "45", "广西" }, { "46", "海南" }, { "50", "重庆" },
				{ "51", "四川" }, { "52", "贵州" }, { "53", "云南" }, { "54", "西藏" },
				{ "61", "陕西" }, { "62", "甘肃" }, { "63", "青海" }, { "64", "宁夏" },
				{ "65", "新疆" }, { "71", "台湾" }, { "81", "香港" }, { "82", "澳门" },
				{ "91", "国外" } };

		// 每位加权因子
		private static final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

		// 判断18位身份证号码是否有效
		private static boolean isValidate18Idcard(String idcard) {
			if (idcard.length() != 18) {
				return false;
			}
			String idcard17 = idcard.substring(0, 17);
			String idcard18Code = idcard.substring(17, 18);
			char c[];
			String checkCode;
			if (isDigital(idcard17)) {
				c = idcard17.toCharArray();
			} else {
				return false;
			}

			if (null != c) {
				int bit[] = converCharToInt(c);
				int sum17 = getPowerSum(bit);

				// 将和值与11取模得到余数进行校验码判断
				checkCode = getCheckCodeBySum(sum17);
				if (null == checkCode) {
					return false;
				}
				// 将身份证的第18位与算出来的校码进行匹配，不相等就为假
				if (!idcard18Code.equalsIgnoreCase(checkCode)) {
					return false;
				}
			}

			return true;
		}

		// 将15位的身份证转成18位身份证
		public static String convertIdcarBy15bit(String idcard) {
			String idcard18 = null;
			if (idcard.length() != 15) {
				return null;
			}

			if (isDigital(idcard)) {
				// 获取出生年月日
				String birthday = idcard.substring(6, 12);
				Date birthdate = null;
				try {
					birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar cday = Calendar.getInstance();
				cday.setTime(birthdate);
				String year = String.valueOf(cday.get(Calendar.YEAR));

				idcard18 = idcard.substring(0, 6) + year + idcard.substring(8);

				char c[] = idcard18.toCharArray();
				String checkCode = "";

				if (null != c) {
					int bit[] = converCharToInt(c);
					int sum17;
					sum17 = getPowerSum(bit);
					// 获取和值与11取模得到余数进行校验码
					checkCode = getCheckCodeBySum(sum17);
					// 获取不到校验位
					if (null == checkCode) {
						return null;
					}

					// 将前17位与第18位校验码拼接
					idcard18 += checkCode;
				}
			} else {
				return null;
			}

			return idcard18;
		}

		// 是否全部由数字组成
		public static boolean isDigital(String str) {
			return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
		}

		// 将身份证的每位和对应位的加权因子相乘之后，再得到和值
		public static int getPowerSum(int[] bit) {
			int sum = 0;
			if (power.length != bit.length) {
				return sum;
			}

			for (int i = 0; i < bit.length; i++) {
				for (int j = 0; j < power.length; j++) {
					if (i == j) {
						sum = sum + bit[i] * power[j];
					}
				}
			}

			return sum;
		}

		// 将和值与11取模得到余数进行校验码判断
		private static String getCheckCodeBySum(int sum17) {
			String checkCode = null;
			switch (sum17 % 11) {
				case 10:
					checkCode = "2";
					break;
				case 9:
					checkCode = "3";
					break;
				case 8:
					checkCode = "4";
					break;
				case 7:
					checkCode = "5";
					break;
				case 6:
					checkCode = "6";
					break;
				case 5:
					checkCode = "7";
					break;
				case 4:
					checkCode = "8";
					break;
				case 3:
					checkCode = "9";
					break;
				case 2:
					checkCode = "x";
					break;
				case 1:
					checkCode = "0";
					break;
				case 0:
					checkCode = "1";
					break;
			}

			return checkCode;
		}

		// 将字符数组转为整型数组
		private static int[] converCharToInt(char[] c) throws NumberFormatException {
			int[] a = new int[c.length];
			int k = 0;
			for (char temp : c) {
				a[k++] = Integer.parseInt(String.valueOf(temp));
			}

			return a;
		}

		// 验证身份证号码是否有效
		public static boolean isValidateIdcard(String idcard) {
			if (!TextUtils.isEmpty(idcard)) {
				if (idcard.length() == 15) {
					return isValidate18Idcard(convertIdcarBy15bit(idcard));
				} else if (idcard.length() == 18) {
					return isValidate18Idcard(idcard);
				}
			}

			return false;
		}
	}

	/**
	 * 判断是否是纯数字或者纯英文
	 *
	 * @param psd
	 * @return
	 */
	public static boolean isContainAll(String psd) {
		Pattern p = Pattern
				.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
		Matcher m = p.matcher(psd);

		return m.matches();
	}
}