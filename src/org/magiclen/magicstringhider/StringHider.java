/*
 *
 * Copyright 2015-2016 magiclen.org
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
 *
 */
package org.magiclen.magicstringhider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <p>
 * 用來隱藏字串的文字內容成一組由大寫英文字母組成的序列號。
 * </p>
 *
 * <ol>
 * <li>使用UTF-16編碼，完整支援多國半、全形語言。</li>
 * <li>能將任意文字隱藏於一組由大寫英文字母(A-Z)組成的序列號中。</li>
 * <li>同一串文字可產生出多種不同的序列號。</li>
 * <li>有序列號壓縮功能(GZIP)。</li>
 * <li>支援金鑰加密。</li>
 * </ol>
 *
 * @author Magic Len
 */
public class StringHider {

    // -----類別變數-----
    /**
     * 是否要輸出例外訊息？
     */
    public static boolean EXCEPTION_OUT = false;

    // -----類別方法-----
    /**
     * <p>
     * 壓縮序列號，若序列號長度太長，可嘗試使用此方法來進行序列號的壓縮，壓縮過後的序列號不一定會比較短，此方法會自行決定是否要使用壓縮後的序列號
     * ，若傳回的序列號有壓縮，將以X開頭。
     * </p>
     *
     * @param originalHiddenString 傳入隱藏著文字的序列號
     * @return 判斷是否要進行壓縮，傳回最佳化之後的序列號，如果壓縮有問題，傳回null
     */
    public static String compression(final String originalHiddenString) {
	if (originalHiddenString != null && originalHiddenString.length() > 0) {
	    final StringBuilder sb = new StringBuilder("");
	    try {
		final byte[] appendBuffer;
		try (ByteArrayOutputStream bais = new ByteArrayOutputStream()) {
		    try (GZIPOutputStream gos = new GZIPOutputStream(bais)) {
			gos.write(originalHiddenString.getBytes("UTF-8"));
		    }
		    appendBuffer = bais.toByteArray();
		}
		if (appendBuffer.length * 2 + 1 > originalHiddenString.length()) {
		    sb.append(originalHiddenString);
		} else {
		    sb.append("X");
		    for (final byte value : appendBuffer) {
			final int intValue = 128 + value;
			sb.append((char) (intValue / 10 + 'A')).append((char) (intValue % 10 + 'A'));
		    }
		}
		return sb.toString();
	    } catch (final Exception ex) {
		if (EXCEPTION_OUT) {
		    ex.printStackTrace(System.out);
		}
		return null;
	    }
	}
	return "";
    }

    /**
     * <p>
     * 解壓縮序列號，可將開頭為X的序列號解壓縮成原本的序列號，若傳入序號未壓縮，則直接傳回未壓縮序列號。
     * </p>
     *
     * @param compressedHiddenString 傳入壓縮序列號
     * @return 傳回解壓縮後的序列號，如果解壓縮有問題，傳回null
     */
    public static String decompression(final String compressedHiddenString) {
	if (compressedHiddenString != null && compressedHiddenString.length() > 0) {
	    final StringBuilder sb = new StringBuilder("");
	    try {
		final byte[] textByte = compressedHiddenString.getBytes("UTF-8");
		if (textByte[0] != 'X') {
		    return compressedHiddenString;
		} else {
		    final int textByteLength = textByte.length;
		    final byte[] bytes = new byte[(textByteLength - 1) / 2];
		    int p = 0;
		    for (int i = 1; i < textByteLength; i += 2) {
			final int intValue = (textByte[i] - 'A') * 10 + (textByte[i + 1] - 'A');
			bytes[p] = (byte) (intValue - 128);
			++p;
		    }

		    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		    final GZIPInputStream gis = new GZIPInputStream(bais);
		    final byte[] buffer = new byte[256];
		    int n;
		    while ((n = gis.read(buffer)) >= 0) {
			if (n > 0) {
			    baos.write(buffer, 0, n);
			}
		    }
		    sb.append(baos.toString("UTF-8"));
		}
		return sb.toString();
	    } catch (final Exception ex) {
		if (EXCEPTION_OUT) {
		    ex.printStackTrace(System.out);
		}
		return null;
	    }
	}
	return "";
    }

    /**
     * 隱藏文字成由大寫英文字母組成的序列號，不使用金鑰，自動添加校驗資料。
     *
     * @param originalString 傳入文字
     * @return 傳回序列號，若隱藏過程有問題，傳回null
     */
    public static String hideString(final String originalString) {
	return hideString(originalString, null, true);
    }

    /**
     * 隱藏文字成由大寫英文字母組成的序列號，並自動添加校驗資料。
     *
     * @param originalString 傳入文字
     * @param key 傳入金鑰
     * @return 傳回序列號，若隱藏過程有問題，傳回null
     */
    public static String hideString(final String originalString, final String key) {
	return hideString(originalString, key, true);
    }

    /**
     * 隱藏文字成由大寫英文字母組成的序列號。
     *
     * @param originalString 傳入文字
     * @param key 傳入金鑰
     * @param addValidation 添加校驗資料
     * @return 傳回序列號，若隱藏過程有問題，傳回null
     */
    public static String hideString(final String originalString, final String key, final boolean addValidation) {
	if (originalString != null && originalString.length() > 0) {
	    final StringBuilder sb = new StringBuilder("");
	    try {
		final int rnd = (int) (Math.random() * 90 + 10);
		final int length = originalString.length() + (key != null ? key.length() : 0);
		final int intLength = getIntLength(length);
		sb.append(String.valueOf((char) ('A' + intLength)));
		final int[] lengthArray = getIntArray(length);
		for (final int value : lengthArray) {
		    sb.append(String.valueOf((char) ('A' + value)));
		}

		final byte[] textByte = originalString.getBytes("UTF-16BE");
		final int textByteLength = textByte.length;
		for (int i = 0; i < textByteLength; i += 2) {
		    final byte MSB = textByte[i], LSB = textByte[i + 1];
		    if (MSB == 0 && LSB >= '0' && LSB <= '9') {
			if (LSB >= '0' && LSB <= '9') { // 數字
			    final int v = LSB - '0';
			    sb.append(hideHide(hideInt(v, rnd), key, rnd));
			} else { // 半形字
			    final int intArray[] = hideEng((char) LSB, rnd);
			    sb.append('Z').append(hideInt(intArray.length, rnd));
			    for (final int intValue : intArray) {
				sb.append(hideHide(hideInt(intValue, rnd), key, rnd));
			    }
			}
		    } else { // 全形字
			final int intArray1[] = hideEng((char) MSB, rnd);
			final int intArray2[] = hideEng((char) LSB, rnd);
			sb.append('Y').append(hideInt(intArray1.length, rnd)).append(hideInt(intArray2.length, rnd));
			for (final int intValue : intArray1) {
			    sb.append(hideHide(hideInt(intValue, rnd), key, rnd));
			}
			for (final int intValue : intArray2) {
			    sb.append(hideHide(hideInt(intValue, rnd), key, rnd));
			}
		    }
		}
		sb.insert(1 + intLength + (originalString.length() / 2), String.valueOf((char) (rnd / 10 + 'A')) + String.valueOf((char) (rnd % 10 + 'A')));
		if (addValidation) {
		    // 添加校驗資料在尾端
		    int stringValue = hashKey(originalString, rnd);
		    while (stringValue > 0) {
			final char temp = hideHide(hideInt(stringValue % 10, rnd), key, rnd);
			sb.append(temp);
			stringValue /= 10;
		    }
		}
		return sb.toString();
	    } catch (final Exception ex) {
		if (EXCEPTION_OUT) {
		    ex.printStackTrace(System.out);
		}
		return null;
	    }
	}
	return "";
    }

    /**
     * 回復由大寫英文字母組成的序列號為文字，傳入的序列號不能被壓縮過(開頭不是X)。
     *
     * @param hiddenString 傳入序列號
     * @return 傳回文字，若回復過程有問題，傳回null
     */
    public static String recoverString(final String hiddenString) {
	return recoverString(hiddenString, null);
    }

    /**
     * 回復由大寫英文字母組成的序列號為文字，傳入的序列號不能被壓縮過(開頭不是X)。
     *
     * @param hiddenString 傳入序列號
     * @param key 傳入金鑰
     * @return 傳回文字，若回復過程有問題，傳回null
     */
    public static String recoverString(final String hiddenString, final String key) {
	if (hiddenString != null && hiddenString.length() > 0) {
	    final StringBuilder sb = new StringBuilder("");
	    //檢查序列號是否都是大寫英文字
	    final char[] chars = hiddenString.toCharArray();
	    for (final char c : chars) {
		if (c < 'A' || c > 'Z') { //如果超出範圍
		    return null;
		}
	    }
	    try {
		int intLength = 0;
		int intLength2 = 0;
		int length = 0;
		int rnd = 0;
		int rndPos = 0;
		int i;
		final byte[] textByte = hiddenString.getBytes("UTF-16BE");
		final int textByteLength = textByte.length;
		for (i = 0; i < textByteLength; i += 2) {
		    if (i == 0) { // 取得長度的長度
			intLength = textByte[i + 1] - 'A';
			if (intLength <= 0) {
			    return null;
			}
			intLength2 = intLength * 2;
		    } else if (i < intLength2) { // 計算原字串的長度
			length = length * 10 + textByte[i + 1] - 'A';
		    } else if (i == intLength2) { // 計算原字串的長度結束
			length = length * 10 + textByte[i + 1] - 'A';
			final int prIntLength = intLength;
			if (key != null) {
			    final int keyLength = key.length();
			    length -= keyLength;
			    intLength = getIntLength(length);
			}
			// 計算亂數種子
			rndPos = (1 + prIntLength + (length / 2)) * 2; // 亂數種子的位置
			rnd = (textByte[rndPos + 1] - 'A') * 10 + textByte[rndPos + 3] - 'A';
			final int actualByteTextLength = textByteLength - 4;
			for (int j = rndPos; j < actualByteTextLength; ++j) {
			    textByte[j] = textByte[j + 4];
			}
			rndPos = textByteLength - 4;
		    } else if (i < rndPos) {
			final byte value = textByte[i + 1];
			switch (value) {
			    case 'Z': // 英文或其他半形文字
			    {
				i += 2;
				final int l = recoverInt((char) textByte[i + 1], rnd);
				int v = 0;
				for (int j = 0; j < l; ++j) {
				    i += 2;
				    v = v * 10 + recoverInt(recoverHide((char) textByte[i + 1], key, rnd), rnd);
				}
				sb.append((char) recoverEng(v, rnd));
			    }
			    break;
			    case 'Y': // 中文或其他全形文字
			    {
				i += 2;
				final int l1 = recoverInt((char) textByte[i + 1], rnd);
				i += 2;
				final int l2 = recoverInt((char) textByte[i + 1], rnd);
				int v1 = 0;
				for (int j = 0; j < l1; ++j) {
				    i += 2;
				    v1 = v1 * 10 + recoverInt(recoverHide((char) textByte[i + 1], key, rnd), rnd);
				}
				int v2 = 0;
				for (int j = 0; j < l2; ++j) {
				    i += 2;
				    v2 = v2 * 10 + recoverInt(recoverHide((char) textByte[i + 1], key, rnd), rnd);
				}
				final byte[] tmp = {-2, -1, (byte) recoverEng(v1, rnd), (byte) recoverEng(v2, rnd)};
				sb.append(new String(tmp, "UTF-16"));
			    }
			    break;
			    default:
				sb.append(recoverInt(recoverHide((char) textByte[i + 1], key, rnd), rnd));
			}
			if (sb.length() == length) {
			    break;
			}
		    }
		}
		if (sb.length() == length) {
		    final String result = sb.toString();
		    if (i == textByteLength - 6) {
			// 不校驗
			return result;
		    } else {
			// 校驗
			int stringValue = hashKey(result, rnd);
			int j = i + 2;
			final int textByteLengthDec6 = textByteLength - 6;
			while (stringValue > 0 && j <= textByteLengthDec6) {
			    final char temp = hideHide(hideInt(stringValue % 10, rnd), key, rnd);
			    if (temp != textByte[j + 1]) {
				return null;
			    }
			    j += 2;
			    stringValue /= 10;
			}
			if (stringValue == 0 && j == textByteLength - 4) {
			    return result;
			} else {
			    return null;
			}
		    }
		} else {
		    return null;
		}
	    } catch (final Exception ex) {
		if (EXCEPTION_OUT) {
		    ex.printStackTrace(System.out);
		}
		return null;
	    }
	}
	return "";
    }

    private static char hideHide(final char str, final String key, final int rnd) {
	if (key != null) {
	    final int hashValue = hashKey(key, rnd);
	    final int value = str - 'A' + hashValue;
	    return (char) ((value % 23) + 'A');
	} else {
	    return str;
	}
    }

    private static char recoverHide(final char str, final String key, final int rnd) {
	if (key != null) {
	    final int hashValue = hashKey(key, rnd);
	    int value = str - 'A' - hashValue;
	    if (value > 23) {
		value = value % 23;
	    } else if (value < 0) {
		final int ratio = (int) Math.ceil(Math.abs(value) / 23.0);
		value += ratio * 23;
	    }
	    return (char) (value + 'A');
	} else {
	    return str;
	}
    }

    private static int hashKey(final String key, final int rnd) {
	if (key != null) {
	    final char[] keyChar = key.toCharArray();
	    int hashValue = 0;
	    for (final char c : keyChar) {
		hashValue = 31 * rnd * hashValue + c;
	    }
	    hashValue = Math.abs(hashValue);
	    if (hashValue > 0 && hashValue % 23 == 0) {
		hashValue--;
	    }
	    return hashValue;
	} else {
	    return 0;
	}
    }

    private static char recoverEng(final int hiddenEng, final int rnd) {
	return (char) (hiddenEng / (rnd % 10 + 1));
    }

    private static char recoverEng(final int[] hiddenEng, final int rnd) {
	int v = 0;
	for (final int intValue : hiddenEng) {
	    v = v * 10 + intValue;
	}
	return recoverEng(v, rnd);
    }

    private static int[] hideEng(final char originalEng, final int rnd) {
	int v = originalEng * (rnd % 10 + 1);
	return getIntArray(v);
    }

    private static char hideInt(final int originalInt, final int rnd) {
	return (char) ('A' + ((originalInt + rnd) % 23));
    }

    private static int recoverInt(final char hiddenChar, final int rnd) {
	int r = hiddenChar - rnd;
	while (r < 'A') {
	    r += 23;
	}
	r -= 'A';
	return r;
    }

    private static int getIntLength(final int value) {
	return String.valueOf(value).length();
    }

    private static int[] getIntArray(final int value) {
	final int array[] = new int[getIntLength(value)];
	final int arrayLength = array.length;
	final String s = String.valueOf(value);
	for (int i = 0; i < arrayLength; ++i) {
	    array[i] = Integer.parseInt(s.substring(i, i + 1));
	}
	return array;
    }

    // -----建構子-----
    /**
     * 私有的建構子，將無法被實體化。
     */
    private StringHider() {

    }
}
