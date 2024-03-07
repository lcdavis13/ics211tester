'''
Copy the following into a .reg file and run it (Make sure that your .py extensions are mapped to Python.File).

Windows Registry Editor Version 5.00

[HKEY_CLASSES_ROOT\Python.File\shellex\DropHandler]
@="{60254CA5-953B-11CF-8C96-00AA00B8708C}"
'''

import sys
import os

def extract_java_code(file_path):
    with open(file_path, 'r', errors='ignore') as file:
        lines = file.readlines()
    
    # Find the start of the valid code
    start_index = None
    for i, line in enumerate(lines):
        if 'package' in line or 'public' in line or 'import' in line:
            start_index = i
            break
    
    # Find the end of the valid code
    end_index = None
    for i in range(len(lines) - 1, -1, -1):
        if '}' in lines[i]:
            end_index = i
            break
    
    # Extract the valid code
    if start_index is not None and end_index is not None and start_index < end_index:
        valid_code = lines[start_index:end_index + 1]
        return ''.join(valid_code)
    else:
        return "Valid code not found"

if __name__ == "__main__":
    file_path = sys.argv[1]  # Get file path from command-line argument
    valid_code = extract_java_code(file_path)
    
    # Export the fixed code to a new file
    fixed_file_path = os.path.splitext(file_path)[0] + '_fixed.java'
    with open(fixed_file_path, 'w') as fixed_file:
        fixed_file.write(valid_code)
    
    print(f"Fixed code has been exported to {fixed_file_path}")
