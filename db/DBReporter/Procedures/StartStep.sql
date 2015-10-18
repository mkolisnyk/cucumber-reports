CREATE PROCEDURE [cukes].[StartStep]
	@RunID BIGINT, 
    @FeatureID BIGINT, 
    @ScenarioID BIGINT, 
    @Keyword VARCHAR(5), 
    @Value VARCHAR(100) 
AS
BEGIN
	INSERT INTO cukes.StepResults (	RunID, FeatureID, ScenarioID, Keyword, Value, StartAt) VALUES (
		@RunID, 
		@FeatureID, 
		@ScenarioID, 
		@Keyword, 
		@Value,
		GETDATE() 
	)
	DECLARE @id BIGINT
	SET @id = SCOPE_IDENTITY();
	RETURN @id
END