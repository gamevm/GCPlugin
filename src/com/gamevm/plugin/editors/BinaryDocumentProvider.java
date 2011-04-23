package com.gamevm.plugin.editors;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import com.gamevm.compiler.assembly.ClassDefinition;
import com.gamevm.compiler.assembly.Instruction;

public class BinaryDocumentProvider extends FileDocumentProvider {
	
	private <I extends Instruction> ClassDefinition<I> readClass(InputStream contentStream) throws IOException {
		return new ClassDefinition<I>(contentStream);
	}
	
	@Override
	protected void setDocumentContent(IDocument document,
			InputStream contentStream, String encoding) throws CoreException {
		
		try {
			ClassDefinition<?> classFile = readClass(contentStream);
			document.set(classFile.toDebugString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new CoreException(STATUS_ERROR);
		}
		
	}

}
