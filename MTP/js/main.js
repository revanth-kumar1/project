(function ($) {
    "use strict";
	//autosize
	$(function(){
				$('.normal').autosize();
			});
			
			
	if($('#search').length>0)
	{
    //reset
		$('button[type="reset"]').click(function(){
			$('#search').attr('disabled',true);
			$('#search').prop('title', 'Enable me by selecting Year and Semester');
			$('#search').prop('style','cursor:not-allowed');
		});
		//SEARCH BUTTON
		if($('#search').length)
		{
			$('#search').prop('style','cursor:not-allowed');
		}
		var num = $('.subsearch').length;
		if(num==2)
		{   
			$('#search').prop('title', 'Enable me by selecting Year and Semester');
			$("#year,#semester").on('mouseup mousedown keyup keydown',function(){
					if($('#year').val()!='Select' && $('#semester').val()!='Select')
					{
					 $('#search').attr('disabled',false);
					 $('#search').prop('style','cursor:pointer');
					 $('#search').prop('title', 'Click me to display records');
					}
				 }); 
			
		}
		else if(num==1)
		{   
			$('#search').prop('title', 'Enable me by selecting Year');
			$("#year").on('mouseup mousedown keyup keydown',function(){
					 if($('#year').val()!='Select' )
					{
					 $('#search').attr('disabled',false);
					 $('#search').prop('style','cursor:pointer');
					 $('#search').prop('title', 'Click me to display records');
					}
			});
		}
		else
		{
			 $('#search').attr('disabled',false);
			 $('#search').prop('style','cursor:pointer');
			 $('#search').prop('title', 'Click me to display records');
		}
	
	}
	
	
	
	//SAVE BUTTON
	//var dateid='';
	$('#save').prop('style','cursor:not-allowed');
	$('#save').prop('title', 'Enable me by filling above fields');
	


//catching number of textarea's
var ta,dt,d;	 
	$('.subsave-area,#id_span,#year,#semester,#save,#search').on('focus keyup keydown mouseup mousedown',function(){
		ta=1;
		$('.subsave-area').each(function () {
				if($(this).val() )
				 {
				 }
				 else
				 {
					  $('#save').attr('disabled',true);
					  $('#save').prop('style','cursor:not-allowed');
					  $('#save').prop('title', 'Enable me by filling above fields');
					  ta=0;
				 }	
		});		
        if($('#search').attr('disabled')!='disabled' && dt==1 && ta==1 && d==1 )
		{
			 $('#save').attr('disabled',false);
			 $('#save').prop('style','cursor:pointer');
			 $('#save').prop('title', 'Click me to Save Records');
		}			
	});


	
//catching datepicker	

	$('.subsave_date,#id_span,#year,#semester').on('change',function(){
		dt=1;
		$('.subsave_date').each(function(){
			if($(this).val())
			 {
				
			 }
			 else
			 {
			      $('#save').attr('disabled',true);
				  $('#save').prop('style','cursor:not-allowed;');
				  $('#save').prop('title', 'Enable me by filling above fields');
				  dt=0;
			 }	
		});
		
		if($('#search').attr('disabled')!='disabled' && dt==1 && ta==1 && d==1) 
		{
			 $('#save').attr('disabled',false);
			 $('#save').prop('style','cursor:pointer');
			 $('#save').prop('title', 'Click me to Save Records');
		}	
	});	
	

//catching dropdowns	
	$('.subsave-drop,#id_span,#year,#semester,#save,#search').on('keyup keydown mouseup mousedown',function(){
		d=1;
		$('.subsave-drop').each(function () {
			 if($(this).val()!='Select')
			 {
				
			 }
			 else
			 {
			      $('#save').attr('disabled',true);
				  $('#save').prop('style','cursor:not-allowed;');
				  $('#save').prop('title', 'Enable me by filling above fields');
				  d=0;
			 }
		});
		if($('#search').attr('disabled')!='disabled' && dt==1 && ta==1 && d==1)
		{
			 $('#save').attr('disabled',false);
			 $('#save').prop('style','cursor:pointer');
			 $('#save').prop('title', 'Click me to Save Records');
		}	
	});


	
//text validation & less than 100
	$('#but').attr('disabled',true);
	$('.subsave-text').on('keyup keydown mouseup mousedown change',function(){
		var y=true;
		$('.subsave-text').each(function(){
			var x=parseInt($(this).val());
			if(!isNaN(x)) 
			{	
				if(x>100)
				{
					y=false;
					return;
				}
			}
			else
			{
			    y=false;
				return ;
			}
			
		});
		
		if(y){$('#but').attr('disabled',false);$('#but').attr('disabled',false);}
		else{$('#but').attr('disabled',true);}
		
		
	});


	
	

})(jQuery);