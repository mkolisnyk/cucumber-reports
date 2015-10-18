CREATE PROCEDURE [cukes].[EndStep]
	@stepID bigint,
	@Matches VARCHAR(100), 
    @Status VARCHAR(10), 
    @ErrorMessage VARCHAR(500), 
    @Duration NUMERIC(10, 4) 
AS
	UPDATE cukes.StepResults SET Matches = @Matches, [Status] = @Status, ErrorMessage = @ErrorMessage, Duration = @Duration, EndAt = GETDATE() WHERE Id = @stepID
RETURN 0
