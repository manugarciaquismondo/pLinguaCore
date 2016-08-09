package org.gcn.plinguacore.simulator.fuzzy;

import static org.gcn.plinguacore.simulator.fuzzy.SafeMode.*;
import static jcuda.driver.JCudaDriver.*;
import jcuda.*;
import jcuda.driver.*;
import jcuda.utils.KernelLauncher;
import jcuda.runtime.cudaComputeMode;
import jcuda.runtime.cudaDeviceProp;
import jcuda.runtime.cudaError;
import jcuda.runtime.dim3;
import jcuda.runtime.JCuda;

import java.io.*;

public class RealFuzzyMatrixCUDA implements RealFuzzyMatrix {
	
	
	public boolean maxMinWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols, boolean max)
	{
			boolean inputCondition = m1rows >= 1 && m2rows >= 1 && m1cols >= 1 && m2cols >= 1
					&& m1rows == m2rows && m1cols == m2cols;

			if(!inputCondition)
				return false;

			// Enable exceptions and omit all subsequent error checks
	        
			JCudaDriver.setExceptionsEnabled(true);

	        // Create the PTX file by calling the NVCC
	        
	        String ptxFileName = null;
	        
	        try{
	        	ptxFileName = preparePtxFile("kernelReal.cu");
	        }
	        catch(IOException ex){}
	        
	        KernelLauncher kernelLauncher = KernelLauncher.load(ptxFileName, "MatrixMaxMinKernel");
	        
	        // Now we prepare the function call
	        
			CUdeviceptr m1d = new CUdeviceptr();
			CUdeviceptr m2d = new CUdeviceptr();
			CUdeviceptr resultd = new CUdeviceptr();
			
			int m1_size 		= Sizeof.FLOAT * m1rows * m1cols;
			int m2_size 		= Sizeof.FLOAT * m2rows * m2cols;
			int result_size 	= Sizeof.FLOAT * m1rows * m2cols;
			
			cuMemAlloc(m1d, m1_size);
			cuMemAlloc(m2d, m2_size);
			cuMemAlloc(resultd, result_size);
			
			cuMemcpyHtoD(m1d, Pointer.to(m1),m1_size);
			cuMemcpyHtoD(m2d, Pointer.to(m2),m2_size);
					
			int block_size = getBlockSize();
			
			int gridCols = m2cols/block_size +  (m2cols%block_size == 0 ? 0:1);
			int gridRows = m1rows/block_size +  (m1rows%block_size == 0 ? 0:1);
			
			dim3 dimGrid = new dim3(gridCols,gridRows,1);
			dim3 dimBlock = new dim3(block_size,block_size,1);
			
			kernelLauncher.setup(dimGrid, dimBlock).call(m1d, m2d, resultd, m1rows, m1cols, m2rows, m2cols, max?1:0, block_size);
	        		
			cuCtxSynchronize();
			
			cuMemcpyDtoH(Pointer.to(result), resultd,result_size);
			
			cuMemFree(m1d);
			cuMemFree(m2d);
			cuMemFree(resultd);
			
			return true;
	}

	
	public boolean timesMaxMinWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols, boolean max)
	{

			boolean inputCondition =
					m1rows >= 1 && m2rows >= 1 &&
					m1cols >= 1 && m2cols >= 1 &&
					m1cols == m2rows;

			if(!inputCondition)
				return false;

			// Enable exceptions and omit all subsequent error checks
	        
			JCudaDriver.setExceptionsEnabled(true);

	        // Create the PTX file by calling the NVCC
	        
	        String ptxFileName = null;
	        
	        try{
	        	ptxFileName = preparePtxFile("kernelReal.cu");
	        }
	        catch(IOException ex){}
	        
	        // KernelLauncher kernelLauncher = KernelLauncher.load(ptxFileName, "MatrixTimesMaxMinKernel");
	        
			int block_size = getBlockSize();
			
			String functionName = null;
			
			
			if(block_size == 16)
				functionName = "MatrixTimesMaxMinKernelExtPrefetch16";
			else
				functionName = "MatrixTimesMaxMinKernelExtPrefetch32";
				
			
			KernelLauncher kernelLauncher = KernelLauncher.load(ptxFileName, functionName);
	        
	        // Now we prepare the function call
	        
			CUdeviceptr m1d = new CUdeviceptr();
			CUdeviceptr m2d = new CUdeviceptr();
			CUdeviceptr resultd = new CUdeviceptr();
			
			int m1_size 		= Sizeof.FLOAT * m1rows * m1cols;
			int m2_size 		= Sizeof.FLOAT * m2rows * m2cols;
			int result_size 	= Sizeof.FLOAT * m1rows * m2cols;
			
			cuMemAlloc(m1d, m1_size);
			cuMemAlloc(m2d, m2_size);
			cuMemAlloc(resultd, result_size);
			
			cuMemcpyHtoD(m1d, Pointer.to(m1),m1_size);
			cuMemcpyHtoD(m2d, Pointer.to(m2),m2_size);
			
			int gridCols = m2cols/block_size +  (m2cols%block_size == 0 ? 0:1);
			int gridRows = m1rows/block_size +  (m1rows%block_size == 0 ? 0:1);
			
			dim3 dimGrid = new dim3(gridCols,gridRows,1);
			dim3 dimBlock = new dim3(block_size,block_size,1);
						
			// kernelLauncher.setup(dimGrid, dimBlock).call(m1d, m2d, resultd, m1rows, m1cols, m2rows, m2cols, max?1:0, block_size);
			
			kernelLauncher.setup(dimGrid, dimBlock).call(m1d, m2d, resultd, m1rows, m1cols, m2rows, m2cols, max?1:0);
	        		
			cuCtxSynchronize();
			
			cuMemcpyDtoH(Pointer.to(result), resultd,result_size);
			
			cuMemFree(m1d);
			cuMemFree(m2d);
			cuMemFree(resultd);			

			return true;
		}

	
	public boolean fire(float[] alpha, float[] a, float[] lambda, float[] out, float[] result, int alpha_rows, int a_rows, int lambda_rows, int out_rows)
	{
		boolean inputCondition = alpha_rows >= 1 && a_rows >= 1 && lambda_rows >= 1 && out_rows >= 1 &&
									alpha_rows == a_rows && a_rows == lambda_rows && lambda_rows == out_rows;

		if(!inputCondition)
			return false;

		// Enable exceptions and omit all subsequent error checks
        
		JCudaDriver.setExceptionsEnabled(true);

        // Create the PTX file by calling the NVCC
        
        String ptxFileName = null;
        
        try{
        	ptxFileName = preparePtxFile("kernelReal.cu");
        }
        catch(IOException ex){}
        
        KernelLauncher kernelLauncher = KernelLauncher.load(ptxFileName, "VectorFireKernel");
        
        // Now we prepare the function call
        
		CUdeviceptr alphad		= new CUdeviceptr();
		CUdeviceptr ad			= new CUdeviceptr();
		CUdeviceptr lambdad		= new CUdeviceptr();
		CUdeviceptr outd		= new CUdeviceptr();
		CUdeviceptr resultd		= new CUdeviceptr();
		
		int alpha_size 		= Sizeof.FLOAT * alpha_rows;
		int a_size 			= Sizeof.FLOAT * a_rows;
		int lambda_size 	= Sizeof.FLOAT * lambda_rows;
		int out_size 		= Sizeof.FLOAT * out_rows;
		int result_size 	= Sizeof.FLOAT * alpha_rows;
		
		cuMemAlloc(alphad, alpha_size);
		cuMemAlloc(ad, a_size);
		cuMemAlloc(lambdad, lambda_size);
		cuMemAlloc(outd, out_size);
		cuMemAlloc(resultd, result_size);
		
		cuMemcpyHtoD(alphad, Pointer.to(alpha),alpha_size);
		cuMemcpyHtoD(ad, Pointer.to(a),a_size);
		cuMemcpyHtoD(lambdad, Pointer.to(lambda),lambda_size);
		cuMemcpyHtoD(outd, Pointer.to(out),out_size);
				
		int block_size = getBlockSize();
		
		int grid = alpha_rows/block_size +  (alpha_rows%block_size == 0 ? 0:1);		
				
		dim3 dimGrid = new dim3(grid,1,1);
		dim3 dimBlock = new dim3(block_size,1,1);
		
		kernelLauncher.setup(dimGrid, dimBlock).call(alphad,ad,lambdad,outd,resultd,alpha_rows,block_size);
        		
		cuCtxSynchronize();
		
		cuMemcpyDtoH(Pointer.to(result), resultd,result_size);
		
		cuMemFree(alphad);
		cuMemFree(ad);
		cuMemFree(lambdad);
		cuMemFree(outd);
		cuMemFree(resultd);			

		return true;

	}
	
	public boolean update(float[] alpha, float[] a, float[] lambda, float[] out, float[] result, int alpha_rows, int a_rows, int lambda_rows, int out_rows)
	{
		boolean inputCondition = alpha_rows >= 1 && a_rows >= 1 && lambda_rows >= 1 && out_rows >= 1 &&
									alpha_rows == a_rows && a_rows == lambda_rows && lambda_rows == out_rows;

		if(!inputCondition)
			return false;

		// Enable exceptions and omit all subsequent error checks
        
		JCudaDriver.setExceptionsEnabled(true);

        // Create the PTX file by calling the NVCC
        
        String ptxFileName = null;
        
        try{
        	ptxFileName = preparePtxFile("kernelReal.cu");
        }
        catch(IOException ex){}
        
        KernelLauncher kernelLauncher = KernelLauncher.load(ptxFileName, "VectorUpdateKernel");
        
        // Now we prepare the function call
        
		CUdeviceptr alphad		= new CUdeviceptr();
		CUdeviceptr ad			= new CUdeviceptr();
		CUdeviceptr lambdad		= new CUdeviceptr();
		CUdeviceptr outd		= new CUdeviceptr();
		CUdeviceptr resultd		= new CUdeviceptr();
		
		int alpha_size 		= Sizeof.FLOAT * alpha_rows;
		int a_size 			= Sizeof.FLOAT * a_rows;
		int lambda_size 	= Sizeof.FLOAT * lambda_rows;
		int out_size 		= Sizeof.FLOAT * out_rows;
		int result_size 	= Sizeof.FLOAT * alpha_rows;
		
		cuMemAlloc(alphad, alpha_size);
		cuMemAlloc(ad, a_size);
		cuMemAlloc(lambdad, lambda_size);
		cuMemAlloc(outd, out_size);
		cuMemAlloc(resultd, result_size);
		
		cuMemcpyHtoD(alphad, Pointer.to(alpha),alpha_size);
		cuMemcpyHtoD(ad, Pointer.to(a),a_size);
		cuMemcpyHtoD(lambdad, Pointer.to(lambda),lambda_size);
		cuMemcpyHtoD(outd, Pointer.to(out),out_size);
				
		int block_size = getBlockSize();
		
		int grid = alpha_rows/block_size +  (alpha_rows%block_size == 0 ? 0:1);		
				
		dim3 dimGrid = new dim3(grid,1,1);
		dim3 dimBlock = new dim3(block_size,1,1);
		
		kernelLauncher.setup(dimGrid, dimBlock).call(alphad,ad,lambdad,outd,resultd,alpha_rows,block_size);
        		
		cuCtxSynchronize();
		
		cuMemcpyDtoH(Pointer.to(result), resultd,result_size);
		
		cuMemFree(alphad);
		cuMemFree(ad);
		cuMemFree(lambdad);
		cuMemFree(outd);
		cuMemFree(resultd);			

		return true;

	}

	public boolean diagonal(float[] v, float[] result, int vrows)
	{
		boolean inputCondition = vrows >= 1;

		if(!inputCondition)
			return false;

		// Enable exceptions and omit all subsequent error checks
        
		JCudaDriver.setExceptionsEnabled(true);

        // Create the PTX file by calling the NVCC
        
        String ptxFileName = null;
        
        try{
        	ptxFileName = preparePtxFile("kernelReal.cu");
        }
        catch(IOException ex){}
        
        KernelLauncher kernelLauncher = KernelLauncher.load(ptxFileName, "MatrixDiagonalKernel");
        
        // Now we prepare the function call
        
		CUdeviceptr vd = new CUdeviceptr();
		CUdeviceptr resultd = new CUdeviceptr();
		
		int v_size 		= Sizeof.FLOAT * vrows;
		int result_size 	= Sizeof.FLOAT * vrows * vrows;
		
		cuMemAlloc(vd, v_size);
		cuMemAlloc(resultd, result_size);
		
		cuMemcpyHtoD(vd, Pointer.to(v),v_size);
				
		int block_size = getBlockSize();
		
		int gridRows = vrows/block_size +  (vrows%block_size == 0 ? 0:1);		
				
		dim3 dimGrid = new dim3(gridRows,gridRows,1);
		dim3 dimBlock = new dim3(block_size,block_size,1);
		
		kernelLauncher.setup(dimGrid, dimBlock).call(vd,resultd,vrows,block_size);
        		
		cuCtxSynchronize();
		
		cuMemcpyDtoH(Pointer.to(result), resultd,result_size);
		
		cuMemFree(vd);
		cuMemFree(resultd);		

		return true;
	}

	public boolean additionWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols)
	{
		boolean inputCondition = m1rows >= 1 && m2rows >= 1 && m1cols >= 1 && m2cols >= 1
				&& m1rows == m2rows && m1cols == m2cols;

		if(!inputCondition)
			return false;

		// Enable exceptions and omit all subsequent error checks
        
		JCudaDriver.setExceptionsEnabled(true);

        // Create the PTX file by calling the NVCC
        
        String ptxFileName = null;
        
        try{
        	ptxFileName = preparePtxFile("kernelReal.cu");
        }
        catch(IOException ex){}
        
        KernelLauncher kernelLauncher = KernelLauncher.load(ptxFileName, "MatrixAddKernel");
        
        // Now we prepare the function call
        
		CUdeviceptr m1d = new CUdeviceptr();
		CUdeviceptr m2d = new CUdeviceptr();
		CUdeviceptr resultd = new CUdeviceptr();
		
		int m1_size 		= Sizeof.FLOAT * m1rows * m1cols;
		int m2_size 		= Sizeof.FLOAT * m2rows * m2cols;
		int result_size 	= Sizeof.FLOAT * m1rows * m2cols;
		
		cuMemAlloc(m1d, m1_size);
		cuMemAlloc(m2d, m2_size);
		cuMemAlloc(resultd, result_size);
		
		cuMemcpyHtoD(m1d, Pointer.to(m1),m1_size);
		cuMemcpyHtoD(m2d, Pointer.to(m2),m2_size);
				
		int block_size = getBlockSize();
		
		int gridCols = m2cols/block_size +  (m2cols%block_size == 0 ? 0:1);
		int gridRows = m1rows/block_size +  (m1rows%block_size == 0 ? 0:1);
		
		dim3 dimGrid = new dim3(gridCols,gridRows,1);
		dim3 dimBlock = new dim3(block_size,block_size,1);
		
		kernelLauncher.setup(dimGrid, dimBlock).call(m1d, m2d, resultd, m1rows, m1cols, m2rows, m2cols, block_size);
        		
		cuCtxSynchronize();
		
		cuMemcpyDtoH(Pointer.to(result), resultd,result_size);
		
		cuMemFree(m1d);
		cuMemFree(m2d);
		cuMemFree(resultd);

		return true;

	}

	public boolean multiplicationWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols)
	{

		boolean inputCondition = m1rows >= 1 && m2rows >= 1 && m1cols >= 1 && m2cols >= 1 &&
				m1cols == m2rows;

		if(!inputCondition)
			return false;
		
		// Enable exceptions and omit all subsequent error checks
        
		JCudaDriver.setExceptionsEnabled(true);

        // Create the PTX file by calling the NVCC
        
        String ptxFileName = null;
        
        try{
        	ptxFileName = preparePtxFile("kernelReal.cu");
        }
        catch(IOException ex){}
        
        // KernelLauncher kernelLauncher = KernelLauncher.load(ptxFileName, "MatrixMulKernel");
        
		int block_size = getBlockSize();
		
		String functionName = null;
		
		if(block_size == 16)
			functionName = "MatrixMulKernelExtPrefetch16";
		else
			functionName = "MatrixMulKernelExtPrefetch32";
			
		
		KernelLauncher kernelLauncher = KernelLauncher.load(ptxFileName, functionName);

        
        // Now we prepare the function call
        
		CUdeviceptr m1d = new CUdeviceptr();
		CUdeviceptr m2d = new CUdeviceptr();
		CUdeviceptr resultd = new CUdeviceptr();
		
		int m1_size 		= Sizeof.FLOAT * m1rows * m1cols;
		int m2_size 		= Sizeof.FLOAT * m2rows * m2cols;
		int result_size 	= Sizeof.FLOAT * m1rows * m2cols;
		
		cuMemAlloc(m1d, m1_size);
		cuMemAlloc(m2d, m2_size);
		cuMemAlloc(resultd, result_size);
		
		cuMemcpyHtoD(m1d, Pointer.to(m1),m1_size);
		cuMemcpyHtoD(m2d, Pointer.to(m2),m2_size);
		
		int gridCols = m2cols/block_size +  (m2cols%block_size == 0 ? 0:1);
		int gridRows = m1rows/block_size +  (m1rows%block_size == 0 ? 0:1);
		
		dim3 dimGrid = new dim3(gridCols,gridRows,1);
		dim3 dimBlock = new dim3(block_size,block_size,1);
				
		kernelLauncher.setup(dimGrid, dimBlock).call(m1d, m2d, resultd, m1rows, m1cols, m2rows, m2cols);
		
		// kernelLauncher.setup(dimGrid, dimBlock).call(m1d, m2d, resultd, m1rows, m1cols, m2rows, m2cols, block_size);
		
		cuCtxSynchronize();
		
		cuMemcpyDtoH(Pointer.to(result), resultd,result_size);
		
		cuMemFree(m1d);
		cuMemFree(m2d);
		cuMemFree(resultd);

		return true;
	}

	public boolean transpose(float[] m, float[] result, int mrows, int mcols, int resrows, int rescols)
	{

		boolean inputCondition = mrows >= 1 && mcols >= 1 && resrows >= 1 && rescols >= 1 && mrows == rescols && mcols == resrows;

		if(!inputCondition)
			return false;

		// Enable exceptions and omit all subsequent error checks
        
		JCudaDriver.setExceptionsEnabled(true);

        // Create the PTX file by calling the NVCC
        
        String ptxFileName = null;
        
        try{
        	ptxFileName = preparePtxFile("kernelReal.cu");
        }
        catch(IOException ex){}
        
        KernelLauncher kernelLauncher = KernelLauncher.load(ptxFileName, "MatrixTransposeKernel");
        
        // Now we prepare the function call
        
		CUdeviceptr md = new CUdeviceptr();
		CUdeviceptr resultd = new CUdeviceptr();
		
		int m_size 		= Sizeof.FLOAT * mrows * mcols;
		int result_size = Sizeof.FLOAT * mrows * mcols;
		
		cuMemAlloc(md, m_size);
		cuMemAlloc(resultd, result_size);
		
		cuMemcpyHtoD(md, Pointer.to(m),m_size);
				
		int block_size = getBlockSize();
		
		  // here we should use values rescols and resrows, but it does not matter because the number of elements are the same
		
		int gridCols = mcols/block_size +  (mcols%block_size == 0 ? 0:1);
		int gridRows = mrows/block_size +  (mrows%block_size == 0 ? 0:1);
		
		dim3 dimGrid = new dim3(gridCols,gridRows,1);
		dim3 dimBlock = new dim3(block_size,block_size,1);
		
		kernelLauncher.setup(dimGrid, dimBlock).call(md,resultd,mrows,mcols,block_size);
        		
		cuCtxSynchronize();
		
		cuMemcpyDtoH(Pointer.to(result), resultd,result_size);
		
		cuMemFree(md);
		cuMemFree(resultd);


		return true;
	}

	public boolean makeUnitVector(float[] result, int dim)
	{
		boolean inputCondition = dim >= 1;

		if(!inputCondition)
			return false;

		int i;

		for(i = 0; i < dim; i++)
			result[i] = 1;

		return true;
	}

	public boolean makeZeroVector(float[] result, int dim)
	{
		boolean inputCondition = dim >= 1;

		if(!inputCondition)
			return false;

		int i;

		for(i = 0; i < dim; i++)
			result[i] = 0;

		return true;
	}

	public void printMatrix(String name, float[] m, int mrows, int mcols){

		System.out.print(name + " =\n");

	        int rows = mrows;
	        int columns = mcols;

	        int i,j;

	        for(i=0;i<rows;i++){
	        	System.out.print("|\t");
	            for(j=0;j<columns;j++){
	            	System.out.print(m[i*columns + j] + "\t");
	            }
	            System.out.print("|\n");
	        }
	}

    private String preparePtxFile(String cuFileName) throws IOException
    {
        int endIndex = cuFileName.lastIndexOf('.');
        if (endIndex == -1)
        {
            endIndex = cuFileName.length()-1;
        }
        String ptxFileName = cuFileName.substring(0, endIndex+1)+"ptx";
        File ptxFile = new File(ptxFileName);
        if (ptxFile.exists())
        {
            return ptxFileName;
        }

        File cuFile = new File(cuFileName);
        if (!cuFile.exists())
        {
            throw new IOException("Input file not found: "+cuFileName);
        }
        String modelString = "-m"+System.getProperty("sun.arch.data.model");
        String command =
            "nvcc " + modelString + " -ptx "+
            cuFile.getPath()+" -o "+ptxFileName;

        System.out.println("Executing\n"+command);
        Process process = Runtime.getRuntime().exec(command);

        String errorMessage =
            new String(toByteArray(process.getErrorStream()));
        String outputMessage =
            new String(toByteArray(process.getInputStream()));
        int exitValue = 0;
        try
        {
            exitValue = process.waitFor();
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new IOException(
                "Interrupted while waiting for nvcc output", e);
        }

        if (exitValue != 0)
        {
            System.out.println("nvcc process exitValue "+exitValue);
            System.out.println("errorMessage:\n"+errorMessage);
            System.out.println("outputMessage:\n"+outputMessage);
            throw new IOException(
                "Could not create .ptx file: "+errorMessage);
        }

        System.out.println("Finished creating PTX file");
        return ptxFileName;
    }

    /**
     * Fully reads the given InputStream and returns it as a byte array
     *
     * @param inputStream The input stream to read
     * @return The byte array containing the data from the input stream
     * @throws IOException If an I/O error occurs
     */
    private byte[] toByteArray(InputStream inputStream)
        throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buffer[] = new byte[8192];
        while (true)
        {
            int read = inputStream.read(buffer);
            if (read == -1)
            {
                break;
            }
            baos.write(buffer, 0, read);
        }
        return baos.toByteArray();
    }
    
    private int getBlockSize()
    {
    	  if(SAFE_MODE)
    		  return 16;
    	
    	  int[] devID = new int[]{-1};
    	  int error;
    	  cudaDeviceProp deviceProp = new cudaDeviceProp();

    	  error = JCuda.cudaGetDevice(devID);

    	  if (error != cudaError.cudaSuccess)
    	  {
    		   System.out.println("cudaGetDevice returned error code " + error);
    	  }

    	  error = JCuda.cudaGetDeviceProperties(deviceProp, devID[0]);

    	  if (deviceProp.computeMode == cudaComputeMode.cudaComputeModeProhibited)
    	  {
    			 System.out.println("Error: device is running in <Compute Mode Prohibited>, no threads can use ::cudaSetDevice()");
    			 return -1;
    	  }

    	  if (error != cudaError.cudaSuccess)
    	  {
    		  System.out.println("cudaGetDevice returned error code " + error);
    	  }
    	  else
    	  {

    	  }

    	  // Use a larger block size for Fermi and above
    	  int block_size = (deviceProp.major < 2) ? 16 : 32;

    	  return block_size;
    }

	
}
