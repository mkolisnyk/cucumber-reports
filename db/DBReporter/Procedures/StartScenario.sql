CREATE PROCEDURE [cukes].[StartScenario]
	@id varchar(100),
	@name varchar(100),
	@description varchar(500),
	@type varchar(20)
AS
BEGIN
	
	DECLARE @total int
	DECLARE @top_version int
	DECLARE @current_version int
	DECLARE @new_id int

	SET @top_version = 0
	SET @current_version = 1
	SET @new_id = 0

	SELECT @top_version = MAX([Version])
	  FROM [cukes].[Scenarios]
	  WHERE [ScenarioID] = @id

	SELECT @total = COUNT(*), @current_version = MAX([Version]) FROM (
	SELECT [Id]
		  ,[Description]
		  ,[Name]
		  ,[Version]
		  ,[ValidFrom]
		  ,[ValidTo]
		  ,BINARY_CHECKSUM([ScenarioID],Name,[Description]) AS CheckSum
		  ,BINARY_CHECKSUM(@id,@name,@description) AS V_CheckSum
	  FROM [cukes].[Scenarios]
	  WHERE [ScenarioID] = @id) AS Selection WHERE [CheckSum] = V_CheckSum

	SELECT @total AS Total, @top_version AS [Top Version], @current_version AS [Current Version]

	IF @total = 0 
	BEGIN
		IF (@top_version is null) 
		BEGIN
			INSERT INTO [cukes].[Scenarios]([ScenarioID]
			  ,[Description]
			  ,[Name]
			  ,[Version]
			  ,[ValidFrom]
			  ,[ValidTo]) VALUES (@id,@description, @name, 1, '01-01-1900', '12-31-9999')
			SELECT @new_id = [Id] FROM [cukes].[Scenarios] WHERE [ScenarioID] = @id AND [Version] = 1
		END
		ELSE
		BEGIN
			UPDATE [cukes].[Scenarios] SET [ValidTo]=GETDATE()  WHERE [ScenarioID] = @id AND [Version] = @top_version
			INSERT INTO [cukes].[Scenarios]([ScenarioID]
			  ,[Description]
			  ,[Name]
			  ,[Version]
			  ,[ValidFrom]
			  ,[ValidTo]) VALUES (@id,@description, @name, @top_version + 1, GETDATE(), '12-31-9999')
			SELECT @new_id = [Id] FROM [cukes].[Scenarios] WHERE [ScenarioID] = @id AND [Version] = @top_version + 1
		END
	END
	ELSE
	BEGIN
		UPDATE [cukes].[Scenarios] SET [ValidTo]=GETDATE()  WHERE [ScenarioID] = @id AND [Version] = @top_version
		UPDATE [cukes].[Scenarios] SET [ValidTo]='12-31-9999'  WHERE [ScenarioID] = @id AND [Version] = @current_version
		SELECT @new_id = [Id] FROM [cukes].[Scenarios] WHERE [ScenarioID] = @id AND [Version] = @current_version
	END

	RETURN @new_id
END