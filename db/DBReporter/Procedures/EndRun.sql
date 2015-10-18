CREATE PROCEDURE [cukes].[EndRun]
	@runID bigint
AS
	UPDATE cukes.Runs SET [End] = GETDATE() WHERE Id = @runID
RETURN 0
