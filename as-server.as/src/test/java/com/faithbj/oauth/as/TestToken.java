package com.faithbj.oauth.as;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestToken {

	public static void main(String[] args) {
		String value="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidW5pdHktY2xpZW50Il0sIm1vYmlsZVBob25lIjoiMTM4MDAxMzgwMDAiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTAzNDYwNDAwLCJlbWFpbCI6ImZhaXRoYmpAMTYzLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI1NjMyNTQ0Mi00ODdmLTQ2M2ItODMyYS0zOGNiY2VkZWZkYmUiLCJjbGllbnRfaWQiOiIxMjM0NTYiLCJ1c2VybmFtZSI6ImFkbWluIn0.IQvxQ7peSrvKFMGcWlVaPCFtWhgoIFbptAafUE5uzr74iz6k37NDdaIKxodBawDoTLGKfzqcsXNTfrB5PSUL3a907Jed2ElF2U2IKGg4KbnofSV1CCc-GhwC4e4-K1b6BEY5oS61QgRHVxRM2lra8slFPVYsdGgENgSEtX1tA3xmZvtQcXpjctRvBmVShm3HOqQaYiOIanODGOr99W1JxMGLFavNgSym74UzOcBRvO8Ov8PW_bpWl3KU4T1y_inRuNMj8YVtQIu7QOMci-2oggrYHYwM7GgWls-T2hy02CiNKkxrpgR1sMVQXWMZ3gXNAZP3MlH30V9ZW9YORJZCkw\",\"token_type\":\"bearer\",\"refresh_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidW5pdHktY2xpZW50Il0sIm1vYmlsZVBob25lIjoiMTM4MDAxMzgwMDAiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXRpIjoiNTYzMjU0NDItNDg3Zi00NjNiLTgzMmEtMzhjYmNlZGVmZGJlIiwiZXhwIjoxNTA1OTk5MzU2LCJlbWFpbCI6ImZhaXRoYmpAMTYzLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJmNjE0YTg3ZC1iZDJlLTQxMDMtOWEyZi01ZjkyODIxMzdiZWYiLCJjbGllbnRfaWQiOiIxMjM0NTYiLCJ1c2VybmFtZSI6ImFkbWluIn0.DIapSncxZ2vGy5cQqYACRMT8Rbgi9JSpTi0m6mDFUPXIeaoN6yq9zYCZV4TPCF7gmxkk65kfJk8aESbXjDgpVN9fsdxqoKZRgq1S_ufCht6dCoGCPyJNARb591iuLmQ0oqu_-VNuXbTWw6kjIXhUjJZvpgX_DYeOp1kiq7DatZV0Wm3GoCDjhCAGix58fQqa-uvCQNk-QYw9NuIgBPvoEIvjgzupzExA9iHEZBb9RsXzkXv2YE8m4r2YKPYcQA7bAqFq9Px1WTN4uhWJKBWO_29TSVY-Se_KDkqoXF0w4fDjE-3sNp8Fb2V2d2EPiO-TsVzB3aVIxBPtMx5skEN9_Q";
		System.out.println(extractTokenKey(value));

	}
	
	public static String extractTokenKey(String value) {
		if (value == null) {
			return null;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(value.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}

}
