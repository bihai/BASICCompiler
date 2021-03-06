/*
 * Copyright (c) 2015, Lorenz Wiest
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of the FreeBSD Project.
 */

package org.basiccompiler.bytecode.constantpoolinfo.impl;

import org.basiccompiler.bytecode.ConstantPool;
import org.basiccompiler.bytecode.constantpoolinfo.ConstantPoolInfo;
import org.basiccompiler.compiler.etc.ByteOutStream;

public class ConstantPoolInfo_String extends ConstantPoolInfo {
	private final int stringIndex; // u2

	public ConstantPoolInfo_String(int stringIndex) {
		super(TAG_STRING);
		this.stringIndex = stringIndex;
	}

	public int getStringIndex() {
		return this.stringIndex;
	}

	@Override
	public void write(ByteOutStream o) {
		super.write(o);
		o.write_u2(this.stringIndex + 1); // NOTE: serialized constant pool indexes are 1-based
	}

	public static int addAndGetIndex(ConstantPool constantPool, String string) {
		String key = getKey(string);
		if (constantPool.contains(key) == false) {
			constantPool.put(key, createInfo(constantPool, string));
		}
		return constantPool.getIndex(key);
	}

	private static String getKey(String string) {
		return "STRING_" + string;
	}

	private static ConstantPoolInfo_String createInfo(ConstantPool constantPool, String string) {
		int stringIndex = ConstantPoolInfo_Utf8.addAndGetIndex(constantPool, string);
		return new ConstantPoolInfo_String(stringIndex);
	}
}
