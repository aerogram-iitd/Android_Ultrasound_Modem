package in.aerogram.eziosense.tone.lib.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import in.aerogram.eziosense.tone.lib.Encoder;
import in.aerogram.eziosense.tone.lib.PlayThread;


public class AudioUtils {

	public static void performData(byte[] data)
			throws IOException {
		PlayThread p = new PlayThread(data);
	}

	public static void performArray(byte[] array)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Encoder.encodeStream(new ByteArrayInputStream(array), baos);
		performData(baos.toByteArray());
	}
}
