/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package phoneticsearch.lucene;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

/**
 * Filter for prefixToken.
 */
public final class PrefixTokenFilter extends TokenFilter {

	private final LinkedList<State> remainingTokens = new LinkedList<>();
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final PositionIncrementAttribute posAtt = addAttribute(PositionIncrementAttribute.class);
	private final int minSize;

	public PrefixTokenFilter(final TokenStream input, final int minSize) {
		super(input);
		this.minSize = minSize;
	}

	@Override
	public boolean incrementToken() throws IOException {
		for (;;) {

			if (!remainingTokens.isEmpty()) {
				// clearAttributes();  // not currently necessary
				restoreState(remainingTokens.removeFirst());
				return true;
			}

			if (!input.incrementToken()) {
				return false;
			}

			final int len = termAtt.length();
			if (len == 0) {
				return true; // pass through zero length terms
			}

			final String v = termAtt.toString();
			termAtt.setEmpty().append(v);
			remainingTokens.addLast(captureState());

			//for (int j = 0; j < minSize - 1; j++) {
			for (int i = v.length() - 1; i >= minSize; i--) {
				final String tokenValue = v.substring(0, i);
				termAtt.setEmpty().append(tokenValue);
				remainingTokens.addLast(captureState());
			}
			//}

			// Just one token to return, so no need to capture/restore
			// any state, simply return it.
			if (remainingTokens.isEmpty()) {
				return true;
			}
		}
	}

	@Override
	public void reset() throws IOException {
		input.reset();
		remainingTokens.clear();
	}
}
