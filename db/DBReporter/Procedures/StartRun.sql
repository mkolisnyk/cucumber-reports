CREATE PROCEDURE [cukes].[StartRun]
	@environment varchar(50),
	@suite varchar(50)
AS
BEGIN
	DECLARE @run_id bigint
	SET @run_id = 0
	INSERT INTO [cukes].[Runs] (Environment, Suite, Start) VALUES (@environment, @suite, GETDATE())
	SET @run_id = SCOPE_IDENTITY();
	RETURN @run_id
END