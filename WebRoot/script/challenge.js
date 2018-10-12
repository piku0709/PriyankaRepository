$(function() {	
	
	//import file
	importFile();
	
});

function importFile() {
	//empty file name
	$("#importFile").val('');
	
	$("#browseButton").click(function(){
		$("#file").click();
	});
	
	$("#file").change(function(){
		var fileName = $(this).val().replace(/^.*[\\\/]/, '');
		if (fileName==null || fileName.length==0) {
			fileName = " ";
		}

		if(fileName.length > 0) {
			$("#importFile").val(fileName);
		}
		
		if(fileName.length > 0) {
			$("#execute").prop("disabled", false);
		}
		
	});
}