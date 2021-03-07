import javax.swing.JOptionPane;

public class RecordOperation {
	
	Employee currentEmployee;
	EmployeeDetails ed;
	RandomFile application;
	long currentByteStart;
	
	public RecordOperation(Employee currentEmployee, EmployeeDetails ed, RandomFile application, long currentByteStart) {
		this.currentEmployee=currentEmployee;
		this.ed = ed;
		this.application=application;
		this.currentByteStart=currentByteStart;
	}
	

	// find byte start in file for first active record
		public long firstRecord() {
			// if any active record in file look for first record
			if (ed.isSomeoneToDisplay()) {
				// open file for reading
				application.openReadFile(ed.file.getAbsolutePath());
				// get byte start in file for first record
				currentByteStart = application.getFirst();
				// assign current Employee to first record in file
				currentEmployee = application.readRecords(currentByteStart);
				application.closeReadFile();// close file for reading
				// if first record is inactive look for next record
				if (currentEmployee.getEmployeeId() == 0)
					nextRecord();// look for next record
			} // end if
			return currentByteStart;
		}// end firstRecord

		// find byte start in file for previous active record
		public long previousRecord() {
			// if any active record in file look for first record
			if (ed.isSomeoneToDisplay()) {
				// open file for reading
				application.openReadFile(ed.file.getAbsolutePath());
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
			return currentByteStart;
		}// end previousRecord

		// find byte start in file for next active record
		public long nextRecord() {
			// if any active record in file look for first record
			if (ed.isSomeoneToDisplay()) {
				// open file for reading
				application.openReadFile(ed.file.getAbsolutePath());
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
			return currentByteStart;
		}// end nextRecord

		// find byte start in file for last active record
		public long lastRecord() {
			// if any active record in file look for first record
			if (ed.isSomeoneToDisplay()) {
				// open file for reading
				application.openReadFile(ed.file.getAbsolutePath());
				// get byte start in file for last record
				currentByteStart = application.getLast();
				// assign current Employee to first record in file
				currentEmployee = application.readRecords(currentByteStart);
				application.closeReadFile();// close file for reading
				// if last record is inactive look for previous record
				if (currentEmployee.getEmployeeId() == 0)
					previousRecord();// look for previous record
			} // end if
			return currentByteStart;
		}// end lastRecord

	
	// add Employee object to fail
		public long addRecord(Employee newEmployee) {
			// open file for writing
			application.openWriteFile(ed.file.getAbsolutePath());
			// write into a file
			currentByteStart = application.addRecords(newEmployee);
			application.closeWriteFile();// close file for writing
			return currentByteStart;
		}// end addRecord
		
		public void deleteRecord() {
			if (ed.isSomeoneToDisplay()) {// if any active record in file display
										// message and delete record
				int returnVal = JOptionPane.showOptionDialog(ed.frame, "Do you want to delete record?", "Delete",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				// if answer yes delete (make inactive - empty) record
				if (returnVal == JOptionPane.YES_OPTION) {
					// open file for writing
					application.openWriteFile(ed.file.getAbsolutePath());
					// delete (make inactive - empty) record in file proper position
					application.deleteRecords(currentByteStart);
					application.closeWriteFile();// close file for writing
					// if any active record in file display next record
					if (ed.isSomeoneToDisplay()) {
						nextRecord();// look for next record
						ed.displayRecords(currentEmployee);
					} // end if
				} // end if
			} // end if
		}// end deleteDecord
		
		public Employee getCurrentEmployee() {
			return currentEmployee;
		}


}
