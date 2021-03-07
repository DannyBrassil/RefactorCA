import javax.swing.JOptionPane;

public class RecordOperation {
	
	
	
	public RecordOperation() {
		
	}
	
	
	
	// find byte start in file for first active record
		private void firstRecord() {
			// if any active record in file look for first record
			if (isSomeoneToDisplay()) {
				// open file for reading
				application.openReadFile(file.getAbsolutePath());
				// get byte start in file for first record
				currentByteStart = application.getFirst();
				// assign current Employee to first record in file
				currentEmployee = application.readRecords(currentByteStart);
				application.closeReadFile();// close file for reading
				// if first record is inactive look for next record
				if (currentEmployee.getEmployeeId() == 0)
					nextRecord();// look for next record
			} // end if
		}// end firstRecord

		// find byte start in file for previous active record
		private void previousRecord() {
			// if any active record in file look for first record
			if (isSomeoneToDisplay()) {
				// open file for reading
				application.openReadFile(file.getAbsolutePath());
				// get byte start in file for previous record
				currentByteStart = application.getPrevious(currentByteStart);
				// assign current Employee to previous record in file
				currentEmployee = application.readRecords(currentByteStart);
				// loop to previous record until Employee is active - ID is not 0
				while (currentEmployee.getEmployeeId() == 0) {
					// get byte start in file for previous record
					currentByteStart = application.getPrevious(currentByteStart);
					// assign current Employee to previous record in file
					currentEmployee = application.readRecords(currentByteStart);
				} // end while
				application.closeReadFile();// close file for reading
			}
		}// end previousRecord

		// find byte start in file for next active record
		private void nextRecord() {
			// if any active record in file look for first record
			if (isSomeoneToDisplay()) {
				// open file for reading
				application.openReadFile(file.getAbsolutePath());
				// get byte start in file for next record
				currentByteStart = application.getNext(currentByteStart);
				// assign current Employee to record in file
				currentEmployee = application.readRecords(currentByteStart);
				// loop to previous next until Employee is active - ID is not 0
				while (currentEmployee.getEmployeeId() == 0) {
					// get byte start in file for next record
					currentByteStart = application.getNext(currentByteStart);
					// assign current Employee to next record in file
					currentEmployee = application.readRecords(currentByteStart);
				} // end while
				application.closeReadFile();// close file for reading
			} // end if
		}// end nextRecord

		// find byte start in file for last active record
		private void lastRecord() {
			// if any active record in file look for first record
			if (isSomeoneToDisplay()) {
				// open file for reading
				application.openReadFile(file.getAbsolutePath());
				// get byte start in file for last record
				currentByteStart = application.getLast();
				// assign current Employee to first record in file
				currentEmployee = application.readRecords(currentByteStart);
				application.closeReadFile();// close file for reading
				// if last record is inactive look for previous record
				if (currentEmployee.getEmployeeId() == 0)
					previousRecord();// look for previous record
			} // end if
		}// end lastRecord

	
	// add Employee object to fail
		public void addRecord(Employee newEmployee) {
			// open file for writing
			application.openWriteFile(file.getAbsolutePath());
			// write into a file
			currentByteStart = application.addRecords(newEmployee);
			application.closeWriteFile();// close file for writing
		}// end addRecord
		
		private void deleteRecord() {
			if (isSomeoneToDisplay()) {// if any active record in file display
										// message and delete record
				int returnVal = JOptionPane.showOptionDialog(frame, "Do you want to delete record?", "Delete",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				// if answer yes delete (make inactive - empty) record
				if (returnVal == JOptionPane.YES_OPTION) {
					// open file for writing
					application.openWriteFile(file.getAbsolutePath());
					// delete (make inactive - empty) record in file proper position
					application.deleteRecords(currentByteStart);
					application.closeWriteFile();// close file for writing
					// if any active record in file display next record
					if (isSomeoneToDisplay()) {
						nextRecord();// look for next record
						displayRecords(currentEmployee);
					} // end if
				} // end if
			} // end if
		}// end deleteDecord


}
