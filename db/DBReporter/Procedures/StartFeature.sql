CREATE PROCEDURE [cukes].[StartFeature]
	@id varchar(100),
	@name varchar(100),
	@description varchar(500)
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
	  FROM [cukes].[Features]
	  WHERE feature_id = @id

	SELECT @total = COUNT(*), @current_version = MAX([Version]) FROM (
	SELECT [Id]
		  ,[Description]
		  ,[Name]
		  ,[Version]
		  ,[ValidFrom]
		  ,[ValidTo]
		  ,BINARY_CHECKSUM(feature_id,Name,[Description]) AS CheckSum
		  ,BINARY_CHECKSUM(@id,@name,@description) AS V_CheckSum
	  FROM [cukes].[Features]
	  WHERE feature_id = @id) AS Selection WHERE [CheckSum] = V_CheckSum

	SELECT @total AS Total, @top_version AS [Top Version], @current_version AS [Current Version]

	IF @total = 0 
	BEGIN
		IF (@top_version is null) 
		BEGIN
			INSERT INTO [cukes].[Features]([feature_id]
			  ,[Description]
			  ,[Name]
			  ,[Version]
			  ,[ValidFrom]
			  ,[ValidTo]) VALUES (@id,@description, @name, 1, '01-01-1900', '12-31-9999')
			SELECT @new_id = [Id] FROM [cukes].[Features] WHERE feature_id = @id AND [Version] = 1
		END
		ELSE
		BEGIN
			UPDATE [cukes].[Features] SET [ValidTo]=GETDATE()  WHERE feature_id = @id AND [Version] = @top_version
			INSERT INTO [cukes].[Features]([feature_id]
			  ,[Description]
			  ,[Name]
			  ,[Version]
			  ,[ValidFrom]
			  ,[ValidTo]) VALUES (@id,@description, @name, @top_version + 1, GETDATE(), '12-31-9999')
			SELECT @new_id = [Id] FROM [cukes].[Features] WHERE feature_id = @id AND [Version] = @top_version + 1
		END
	END
	ELSE
	BEGIN
		UPDATE [cukes].[Features] SET [ValidTo]=GETDATE()  WHERE feature_id = @id AND [Version] = @top_version
		UPDATE [cukes].[Features] SET [ValidTo]='12-31-9999'  WHERE feature_id = @id AND [Version] = @current_version
		SELECT @new_id = [Id] FROM [cukes].[Features] WHERE feature_id = @id AND [Version] = @current_version
	END

	RETURN @new_id
END